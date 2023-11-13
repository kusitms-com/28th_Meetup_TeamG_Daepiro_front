package com.daepiro.numberoneproject.presentation.di

import android.app.Application
import com.daepiro.numberoneproject.BuildConfig
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication: Application() {
    val KAKAO = BuildConfig.KAKAO_NATIVE_APP_KEY
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this,KAKAO)
    }
}