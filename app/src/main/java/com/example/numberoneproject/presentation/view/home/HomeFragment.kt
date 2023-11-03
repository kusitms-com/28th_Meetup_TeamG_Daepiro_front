package com.example.numberoneproject.presentation.view.home

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.numberoneproject.R
import com.example.numberoneproject.data.model.ShelterRequestBody
import com.example.numberoneproject.databinding.FragmentHomeBinding
import com.example.numberoneproject.presentation.base.BaseFragment
import com.example.numberoneproject.presentation.util.Extensions.repeatOnStarted
import com.example.numberoneproject.presentation.viewmodel.ShelterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.util.Locale


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    val shelterVM by viewModels<ShelterViewModel>()
    private lateinit var aroundShelterAdapter: AroundShelterAdapter
    private val startLocation = Pair(37.549186395087, 127.07505567644)      // 출발지 위도, 경도
    private val endLocation = Pair(37.42637222, 126.9898)     // 도착지 위도, 경도

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNaverMap.setOnClickListener {
            searchLoadToNaverMap()
        }

        binding.btnKakaoMap.setOnClickListener {
            searchLoadToKakaoMap()
        }

        binding.btnTMap.setOnClickListener {
            searchLoadToTMap()
        }

        aroundShelterAdapter = AroundShelterAdapter()

        binding.vpShelter.apply {
            offscreenPageLimit = 1
            adapter = aroundShelterAdapter

            setPageTransformer(MarginPageTransformer(32))
            setPadding(0,0,150,0)
        }

        shelterVM.getAroundSheltersList(ShelterRequestBody(37.5559, 126.9723, "민방위"))
    }

    override fun subscribeUi() {
        repeatOnStarted {
            shelterVM.sheltersList.collectLatest {
                aroundShelterAdapter.setData(it.shelterList)
            }
        }
    }

    private fun searchLoadToNaverMap() {
        val geocoder = Geocoder(requireContext(), Locale.KOREAN)
        val startLocationAddress = geocoder.getFromLocation(startLocation.first, startLocation.second, 1)
        val endLocationAddress = geocoder.getFromLocation(endLocation.first, endLocation.second, 1)
        val encodedStartAddress = encodeAddress(startLocationAddress?.get(0)?.getAddressLine(0).toString().replace("대한민국 ",""))
        val encodedEndAddress = encodeAddress(endLocationAddress?.get(0)?.getAddressLine(0).toString().replace("대한민국 ",""))

        val url = "nmap://route/walk?slat=${startLocation.first}&slng=${startLocation.second}&sname=${encodedStartAddress}&dlat=${endLocation.first}&dlng=${endLocation.second}&dname=${encodedEndAddress}"
        val storeUrl = "market://details?id=com.nhn.android.nmap"

        searchUrlToLoadMap(url, storeUrl)
    }

    private fun searchLoadToKakaoMap() {
        val url ="kakaomap://route?sp=${startLocation.first},${startLocation.second}&ep=${endLocation.first},${endLocation.second}&by=FOOT"
        val storeUrl = "market://details?id=net.daum.android.map"

        searchUrlToLoadMap(url, storeUrl)
    }

    private fun searchLoadToTMap() {
        val url = "tmap://route?startx=${startLocation.second}&starty=${startLocation.first}&goalx=${endLocation.second}&goaly=${endLocation.first}&reqCoordType=WGS84&resCoordType=WGS84"
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