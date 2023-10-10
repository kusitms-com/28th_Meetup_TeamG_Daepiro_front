package com.example.numberoneproject.presentation.di

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this,"0786b07056f57dd7e119842166e52498")
    }
}