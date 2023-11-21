package com.daepiro.numberoneproject.presentation.view.home

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.viewpager2.widget.MarginPageTransformer
import com.daepiro.numberoneproject.R
import com.daepiro.numberoneproject.data.model.DisasterRequestBody
import com.daepiro.numberoneproject.data.model.ShelterRequestBody
import com.daepiro.numberoneproject.databinding.FragmentHomeBinding
import com.daepiro.numberoneproject.presentation.base.BaseFragment
import com.daepiro.numberoneproject.presentation.util.Extensions.repeatOnStarted
import com.daepiro.numberoneproject.presentation.util.Extensions.showToast
import com.daepiro.numberoneproject.presentation.viewmodel.DisasterViewModel
import com.daepiro.numberoneproject.presentation.viewmodel.ShelterViewModel
import com.google.android.gms.common.util.ClientLibraryUtils.getPackageInfo
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.util.Locale

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    val shelterVM by viewModels<ShelterViewModel>()
    val disasterVM by viewModels<DisasterViewModel>()

    private lateinit var aroundShelterAdapter: AroundShelterAdapter
    private lateinit var disasterCheckListAdapter: DisasterCheckListAdapter

    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null
    private lateinit var mLocationRequest: LocationRequest //
    private lateinit var mLastLocation: Location
    private lateinit var userLocation: Pair<Double, Double>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.disasterVM = disasterVM
        binding.shelterVM = shelterVM


        mLocationRequest =  LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        binding.tvRefresh.setOnClickListener {
            disasterVM.getDisasterMessage(DisasterRequestBody(userLocation.first, userLocation.second))
        }

        binding.ivExpand.setOnClickListener {
            disasterVM.changeExpandedState()
        }

        binding.tvShelterAll.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToAroundShelterDetailFragment(
                latitude = userLocation.first.toFloat(),
                longitude = userLocation.second.toFloat(),
                address = changeToAddress()
            )
            findNavController().navigate(action)
        }

        binding.llAlarm.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToAlarmDetailFragment()
            findNavController().navigate(action)
        }
    }

    override fun setupInit() {
        //로컬에 대피소 저장하기 위해 호출
        shelterVM.getSheltersetLocal()

        requestPermission()
        setSheltersViewPager()
        setCheckListViewPager()


    }

    private fun requestPermission() {
        TedPermission.create()
            .setPermissionListener(object : PermissionListener {
                override fun onPermissionGranted() {
                    startLocationUpdates()
                }

                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                    showToast("위치 권한과 알림 권한은 필수로 필요합니다.")
                }
            })
            .setDeniedMessage("권한을 허용해주세요. [설정] > [앱 및 알림] > [고급] > [앱 권한]")
            .setPermissions(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.POST_NOTIFICATIONS
            )
            .setPermissions(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION)
            .check()
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            // 시스템에서 받은 location 정보를 onLocationChanged()에 전달
            onLocationChanged(locationResult.lastLocation)
        }
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())

        if (isEnableLocationSystem()) {
            Looper.myLooper()?.let {
                mFusedLocationProviderClient!!.requestLocationUpdates(mLocationRequest, mLocationCallback, it)
            }
        } else {
            LocationOffDialogFragment().show(parentFragmentManager, "")
        }
    }

    // 시스템으로 부터 받은 위치 정보를 화면에 갱신해주는 메소드
    fun onLocationChanged(location: Location) {
        mLastLocation = location
        userLocation = Pair(mLastLocation.latitude, mLastLocation.longitude) // 갱신 된 위도

        disasterVM.getDisasterMessage(DisasterRequestBody(userLocation.first, userLocation.second))
        shelterVM.getAroundSheltersList(ShelterRequestBody(userLocation.first, userLocation.second, "민방위"))
    }

    private fun isEnableLocationSystem(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val locationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as? LocationManager
            locationManager?.isLocationEnabled!!
        } else{
            val mode = Settings.Secure.getInt(requireContext().contentResolver, Settings.Secure.LOCATION_MODE, Settings.Secure.LOCATION_MODE_OFF)
            mode != Settings.Secure.LOCATION_MODE_OFF
        }
    }

    private fun setSheltersViewPager() {
        binding.cgAroundShelter.setOnCheckedStateChangeListener { group, checkedIds ->
            shelterVM.shelterLoadingState.value = true

            if (R.id.chip_around_shelter_all in checkedIds) {
                shelterVM.getAroundSheltersList(ShelterRequestBody(userLocation.first, userLocation.second, null))
            } else if (R.id.chip_around_shelter_1 in checkedIds) {
                shelterVM.getAroundSheltersList(ShelterRequestBody(userLocation.first, userLocation.second, "지진"))
            } else if (R.id.chip_around_shelter_2 in checkedIds) {
                shelterVM.getAroundSheltersList(ShelterRequestBody(userLocation.first, userLocation.second, "수해"))
            } else if (R.id.chip_around_shelter_3 in checkedIds) {
                shelterVM.getAroundSheltersList(ShelterRequestBody(userLocation.first, userLocation.second, "민방위"))
            }
        }

        aroundShelterAdapter = AroundShelterAdapter()
        binding.vpShelter.apply {
            offscreenPageLimit = 1
            adapter = aroundShelterAdapter

            setPageTransformer(MarginPageTransformer(32))
            setPadding(0,0,150,0)
        }

        aroundShelterAdapter.setItemClickListener(object : AroundShelterAdapter.OnItemClickListener {
            override fun onClickNaverMap(v: View, position: Int) {
                CoroutineScope(Dispatchers.Main).launch {
                    val lat = shelterVM.sheltersList.first().shelterList[position].latitude
                    val lng = shelterVM.sheltersList.first().shelterList[position].longitude
                    searchLoadToNaverMap(lat,lng)
                }
            }

            override fun onClickKakaoMap(v: View, position: Int) {
                CoroutineScope(Dispatchers.Main).launch {
                    val lat = shelterVM.sheltersList.first().shelterList[position].latitude
                    val lng = shelterVM.sheltersList.first().shelterList[position].longitude
                    searchLoadToKakaoMap(lat,lng)
                }
            }

            override fun onClickTMap(v: View, position: Int) {
                CoroutineScope(Dispatchers.Main).launch {
                    val lat = shelterVM.sheltersList.first().shelterList[position].latitude
                    val lng = shelterVM.sheltersList.first().shelterList[position].longitude
                    searchLoadToTMap(lat,lng)
                }
            }
        })
    }

    private val checkList1 = listOf("실내1","실내2","실내3","실내4","실내5")
    private val checkList2 = listOf("실외1","실외22", "실외 333")
    private val checkList3 = listOf("기타","기타기타기타기타", "123213123")
    private var selectedCheckList = 1

    private fun setCheckListViewPager() {
        binding.cgCheckList.setOnCheckedStateChangeListener { group, checkedIds ->
            disasterVM.checkListIsExpanded.value = false
            if (R.id.chip_check_list_1 in checkedIds) {
                selectedCheckList = 1
                disasterCheckListAdapter.setData(checkList1.subList(0,3))
            } else if (R.id.chip_check_list_2 in checkedIds) {
                selectedCheckList = 2
                disasterCheckListAdapter.setData(checkList2.subList(0,3))
            } else if (R.id.chip_check_list_3 in checkedIds) {
                selectedCheckList = 3
                disasterCheckListAdapter.setData(checkList3.subList(0,3))
            }
        }

        disasterCheckListAdapter = DisasterCheckListAdapter()

        binding.rvCheckList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = disasterCheckListAdapter
        }

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.rvCheckList)
    }

    override fun subscribeUi() {
        repeatOnStarted {
            shelterVM.sheltersList.collectLatest {
                aroundShelterAdapter.setData(it.shelterList)
            }
        }

        repeatOnStarted {
            disasterVM.checkListIsExpanded.collectLatest {
                if (it) {
                    when(selectedCheckList) {
                        1 -> disasterCheckListAdapter.setData(checkList1)
                        2 -> disasterCheckListAdapter.setData(checkList2)
                        3 -> disasterCheckListAdapter.setData(checkList3)
                    }
                    binding.ivExpand.setImageDrawable(requireContext().getDrawable(R.drawable.ic_arrow_top))
                } else {
                    when(selectedCheckList) {
                        1 -> disasterCheckListAdapter.setData(checkList1.subList(0,3))
                        2 -> disasterCheckListAdapter.setData(checkList2.subList(0,3))
                        3 -> disasterCheckListAdapter.setData(checkList3.subList(0,3))
                    }
                    binding.ivExpand.setImageDrawable(requireContext().getDrawable(R.drawable.ic_arrow_down))
                }
            }
        }
    }

    private fun changeToAddress(): String {
        val geocoder = Geocoder(requireContext(), Locale.KOREAN)
        val address = geocoder.getFromLocation(userLocation.first, userLocation.second, 1)
        return address?.get(0)?.getAddressLine(0).toString().replace("대한민국 ","")
    }

    private val STORE_URL = "market://details?id="
    private fun searchLoadToNaverMap(latitude: Double, longitude: Double) {
        val geocoder = Geocoder(requireContext(), Locale.KOREAN)
        val startLocationAddress = geocoder.getFromLocation(userLocation.first, userLocation.second, 1)
        val endLocationAddress = geocoder.getFromLocation(latitude, longitude, 1)
        val encodedStartAddress = encodeAddress(startLocationAddress?.get(0)?.getAddressLine(0).toString().replace("대한민국 ",""))
        val encodedEndAddress = encodeAddress(endLocationAddress?.get(0)?.getAddressLine(0).toString().replace("대한민국 ",""))

        val url = "nmap://route/walk?slat=${userLocation.first}&slng=${userLocation.second}&sname=${encodedStartAddress}&dlat=${latitude}&dlng=${longitude}&dname=${encodedEndAddress}"
        val storeUrl = "com.nhn.android.nmap"

        searchUrlToLoadMap(url, storeUrl)
    }

    private fun searchLoadToKakaoMap(latitude: Double, longitude: Double) {
        val url ="kakaomap://route?sp=${userLocation.first},${userLocation.second}&ep=${latitude},${longitude}&by=FOOT"
        val storeUrl = "net.daum.android.map"

        searchUrlToLoadMap(url, storeUrl)
    }

    private fun searchLoadToTMap(latitude: Double, longitude: Double) {
        val url = "tmap://route?startx=${userLocation.second}&starty=${userLocation.first}&goalx=${longitude}&goaly=${latitude}&reqCoordType=WGS84&resCoordType=WGS84"
        val storeUrl = "com.skt.tmap.ku"

        searchUrlToLoadMap(url, storeUrl)
    }

    private fun searchUrlToLoadMap(url: String, storeUrl: String) {
        if (isAppInstalled(storeUrl, requireContext().packageManager)) {
            // 앱이 설치되어 있으면 앱 실행
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        } else {
            // 앱이 설치되어 있지 않으면 스토어로 이동
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(STORE_URL + storeUrl))
            startActivity(intent)
        }
    }

    private fun isAppInstalled(packageName : String, packageManager : PackageManager) : Boolean {
        return try{
            packageManager.getPackageInfo(packageName, 0)
            true
        }catch (ex : PackageManager.NameNotFoundException){
            false
        }
    }

    private fun encodeAddress(address: String): String {
        return URLEncoder.encode(address, StandardCharsets.UTF_8.toString())
    }
}