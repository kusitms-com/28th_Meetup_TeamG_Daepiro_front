package com.example.numberoneproject.presentation.view.home

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.numberoneproject.R
import com.example.numberoneproject.databinding.FragmentHomeBinding
import com.example.numberoneproject.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.util.Locale


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
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

    }

    private fun searchLoadToNaverMap() {
        val geocoder = Geocoder(requireContext(), Locale.KOREAN)
        val startLocationAddress = geocoder.getFromLocation(startLocation.first, startLocation.second, 1)
        val endLocationAddress = geocoder.getFromLocation(endLocation.first, endLocation.second, 1)
        val encodedStartAddress = encodeAddress(startLocationAddress?.get(0)?.getAddressLine(0).toString().replace("대한민국 ",""))
        val encodedEndAddress = encodeAddress(endLocationAddress?.get(0)?.getAddressLine(0).toString().replace("대한민국 ",""))

        val url = "nmap://route/walk?slat=${startLocation.first}&slng=${startLocation.second}&sname=${encodedStartAddress}&dlat=${endLocation.first}&dlng=${endLocation.second}&dname=${encodedEndAddress}&appname=com.example.numberoneproject"

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

        // 네이버맵이 설치되어 있다면 앱으로 연결, 설치되어 있지 않다면 스토어로 이동
        if (installCheck.isEmpty()) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.nhn.android.nmap")))
        } else {
            startActivity(intent)
        }
    }

    private fun searchLoadToKakaoMap() {
        val url ="kakaomap://route?sp=${startLocation.first},${endLocation.first}&ep=${startLocation.second},${endLocation.second}&by=FOOT"

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

        // 카카오맵이 설치되어 있다면 앱으로 연결, 설치되어 있지 않다면 스토어로 이동
        if (installCheck.isEmpty()) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=net.daum.android.map")))
        } else {
            startActivity(intent)
        }
    }

    private fun searchLoadToTMap() {

    }

    private fun encodeAddress(address: String): String {
        return URLEncoder.encode(address, StandardCharsets.UTF_8.toString())
    }
}