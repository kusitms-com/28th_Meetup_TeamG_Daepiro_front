package com.example.numberoneproject.presentation.view.home

import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import com.example.numberoneproject.R
import com.example.numberoneproject.databinding.FragmentHomeBinding
import com.example.numberoneproject.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val startLocation = Pair(37.537229,37.4979502)      // 출발지 위도, 경도
    private val endLocation = Pair(127.005515, 127.0276368)     // 도착지 위도, 경도

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
        val url = "nmap://route/walk?slat=${startLocation.first}&slng=${startLocation.second}&dlat=${endLocation.first}&dlng=${endLocation.second}&appname=com.example.numberoneproject"

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
}