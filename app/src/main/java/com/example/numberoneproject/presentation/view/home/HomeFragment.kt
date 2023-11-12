package com.example.numberoneproject.presentation.view.home

import android.annotation.SuppressLint
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
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.numberoneproject.R
import com.example.numberoneproject.data.model.DisasterRequestBody
import com.example.numberoneproject.data.model.ShelterRequestBody
import com.example.numberoneproject.databinding.FragmentHomeBinding
import com.example.numberoneproject.presentation.base.BaseFragment
import com.example.numberoneproject.presentation.util.Extensions.repeatOnStarted
import com.example.numberoneproject.presentation.util.Extensions.showToast
import com.example.numberoneproject.presentation.viewmodel.DisasterViewModel
import com.example.numberoneproject.presentation.viewmodel.ShelterViewModel
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
    private val checkList = listOf("1","2","3","4","5")

    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null
    private lateinit var mLocationRequest: LocationRequest //
    private lateinit var mLastLocation: Location
    private lateinit var userLocation: Pair<Double, Double>
    private var userAddress = ""


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.disasterVM = disasterVM
        binding.shelterVM = shelterVM
      
        //로컬에 대피소 저장하기 위해 호출
        shelterVM.getSheltersetLocal()

        mLocationRequest =  LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        binding.ivExpand.setOnClickListener {
            disasterVM.changeExpandedState()
        }

        binding.tvShelterAll.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToAroundShelterDetailFragment(
                latitude = userLocation.first.toFloat(),
                longitude = userLocation.second.toFloat(),
                address = userAddress
            )
            findNavController().navigate(action)
        }
    }

    override fun setupInit() {
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
                    showToast("위치 권한이 필수로 필요합니다.")
                }
            })
            .setDeniedMessage("권한을 허용해주세요. [설정] > [앱 및 알림] > [고급] > [앱 권한]")
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
                shelterVM.getAroundSheltersList(ShelterRequestBody(userLocation.first, userLocation.second, "민방위"))
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

    private fun setCheckListViewPager() {
        binding.cgCheckList.setOnCheckedStateChangeListener { group, checkedIds ->
            if (R.id.chip_check_list_1 in checkedIds) {
            } else if (R.id.chip_check_list_2 in checkedIds) {
            } else if (R.id.chip_check_list_3 in checkedIds) {
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
                    disasterCheckListAdapter.setData(checkList)
                    binding.ivExpand.setImageDrawable(requireContext().getDrawable(R.drawable.ic_arrow_top))
                } else {
                    disasterCheckListAdapter.setData(checkList.subList(0,3))
                    binding.ivExpand.setImageDrawable(requireContext().getDrawable(R.drawable.ic_arrow_down))
                }
            }
        }

        repeatOnStarted {
            disasterVM.disasterMessage.collectLatest {
//                userAddress = it.info.split(" ・")[0]
            }
        }
    }

    private fun searchLoadToNaverMap(latitude: Double, longitude: Double) {
        val geocoder = Geocoder(requireContext(), Locale.KOREAN)
        val startLocationAddress = geocoder.getFromLocation(userLocation.first, userLocation.second, 1)
        val endLocationAddress = geocoder.getFromLocation(latitude, longitude, 1)
        val encodedStartAddress = encodeAddress(startLocationAddress?.get(0)?.getAddressLine(0).toString().replace("대한민국 ",""))
        val encodedEndAddress = encodeAddress(endLocationAddress?.get(0)?.getAddressLine(0).toString().replace("대한민국 ",""))

        val url = "nmap://route/walk?slat=${userLocation.first}&slng=${userLocation.second}&sname=${encodedStartAddress}&dlat=${latitude}&dlng=${longitude}&dname=${encodedEndAddress}"
        val storeUrl = "market://details?id=com.nhn.android.nmap"

        searchUrlToLoadMap(url, storeUrl)
    }

    private fun searchLoadToKakaoMap(latitude: Double, longitude: Double) {
        val url ="kakaomap://route?sp=${userLocation.first},${userLocation.second}&ep=${latitude},${longitude}&by=FOOT"
        val storeUrl = "market://details?id=net.daum.android.map"

        searchUrlToLoadMap(url, storeUrl)
    }

    private fun searchLoadToTMap(latitude: Double, longitude: Double) {
        val url = "tmap://route?startx=${userLocation.second}&starty=${userLocation.first}&goalx=${longitude}&goaly=${latitude}&reqCoordType=WGS84&resCoordType=WGS84"
        val storeUrl = "market://details?id=com.skt.tmap.ku"

        searchUrlToLoadMap(url, storeUrl)
    }

    private fun searchUrlToLoadMap(url: String, storeUrl: String) {
        val intent =  Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.addCategory(Intent.CATEGORY_BROWSABLE)

        val installCheck = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireContext().packageManager.queryIntentActivities(
                Intent(Intent.ACTION_MAIN, null).addCategory(Intent.CATEGORY_LAUNCHER),
                PackageManager.ResolveInfoFlags.of(PackageManager.MATCH_DEFAULT_ONLY.toLong())
            )
        } else {
            requireContext().packageManager.queryIntentActivities(
                Intent(Intent.ACTION_MAIN, null).addCategory(Intent.CATEGORY_LAUNCHER),
                PackageManager.GET_META_DATA
            )
        }

        // 이동할 지도앱이 설치되어 있다면 앱으로 연결, 설치되어 있지 않다면 스토어로 이동
        if (installCheck.isEmpty()) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(storeUrl)))
        } else {
            startActivity(intent)
        }
    }

    private fun encodeAddress(address: String): String {
        return URLEncoder.encode(address, StandardCharsets.UTF_8.toString())
    }
}