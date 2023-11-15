package com.daepiro.numberoneproject.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.daepiro.numberoneproject.data.network.ApiResult
import com.daepiro.numberoneproject.domain.usecase.RefreshAccessTokenUseCase
import com.daepiro.numberoneproject.presentation.util.SomeWorker
import com.daepiro.numberoneproject.presentation.util.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    private val refreshAccessTokenUseCase: RefreshAccessTokenUseCase,
) : ViewModel() {

    fun postMyLocation() {
        viewModelScope.launch {
            val periodicWorkRequest = PeriodicWorkRequestBuilder<SomeWorker>(15, TimeUnit.MINUTES).build()
            val workManager = WorkManager.getInstance()
            workManager.enqueue(periodicWorkRequest)

            workManager.getWorkInfoByIdLiveData(periodicWorkRequest.id).observeForever {
                if (it.state.isFinished) {
                    Log.d("taag", "워크매니저 Finish")
                } else {
                    Log.d("taag", "워크매니저 작동 중")
                }
            }
        }
    }
}