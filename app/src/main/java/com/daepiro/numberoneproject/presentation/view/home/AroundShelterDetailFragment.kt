package com.daepiro.numberoneproject.presentation.view.home

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.daepiro.numberoneproject.R
import com.daepiro.numberoneproject.data.model.ShelterRequestBody
import com.daepiro.numberoneproject.databinding.FragmentAroundShelterDetailBinding
import com.daepiro.numberoneproject.presentation.base.BaseFragment
import com.daepiro.numberoneproject.presentation.util.Extensions.repeatOnStarted
import com.daepiro.numberoneproject.presentation.viewmodel.ShelterViewModel
import com.google.android.material.tabs.TabLayout
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
class AroundShelterDetailFragment: BaseFragment<FragmentAroundShelterDetailBinding>(R.layout.fragment_around_shelter_detail) {
    private val args by navArgs<AroundShelterDetailFragmentArgs>()
    val shelterVM by viewModels<ShelterViewModel>()
    private lateinit var userLocation: Pair<Double, Double>

    private lateinit var aroundShelterAllAdapter: AroundShelterAllAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userLocation = Pair(args.latitude.toDouble(), args.longitude.toDouble())
        shelterVM.getAroundSheltersList(ShelterRequestBody(userLocation.first, userLocation.second, "민방위"))
        binding.tvAddress.text = args.address

        setAroundShelterRV()

        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }

    }

    override fun setupInit() {
        binding.tlCategory.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position) {
                    0 -> shelterVM.getAroundSheltersList(ShelterRequestBody(userLocation.first, userLocation.second, null))
                    1 -> shelterVM.getAroundSheltersList(ShelterRequestBody(userLocation.first, userLocation.second, "지진"))
                    2 -> shelterVM.getAroundSheltersList(ShelterRequestBody(userLocation.first, userLocation.second, "수해"))
                    3 -> shelterVM.getAroundSheltersList(ShelterRequestBody(userLocation.first, userLocation.second, "민방위"))
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) { }
            override fun onTabReselected(tab: TabLayout.Tab?) { }
        })
    }

    private fun setAroundShelterRV() {
        aroundShelterAllAdapter = AroundShelterAllAdapter()

        binding.rvShelter.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = aroundShelterAllAdapter
        }

        aroundShelterAllAdapter.setItemClickListener(object : AroundShelterAllAdapter.OnItemClickListener {
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

    override fun subscribeUi() {
        repeatOnStarted {
            shelterVM.sheltersList.collectLatest {
                aroundShelterAllAdapter.setData(it.shelterList)
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

    private val STORE_URL = "market://details?id="
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