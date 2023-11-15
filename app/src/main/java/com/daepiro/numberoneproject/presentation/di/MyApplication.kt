package com.daepiro.numberoneproject.presentation.di

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.daepiro.numberoneproject.BuildConfig
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MyApplication: Application(), Configuration.Provider {
    @Inject lateinit var workerFactory: HiltWorkerFactory

    val KAKAO = BuildConfig.KAKAO_NATIVE_APP_KEY

    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this,KAKAO)
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
    }
}