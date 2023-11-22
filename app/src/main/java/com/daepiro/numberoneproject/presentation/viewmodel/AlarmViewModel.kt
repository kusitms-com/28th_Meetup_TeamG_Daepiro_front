package com.daepiro.numberoneproject.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daepiro.numberoneproject.data.model.Contents
import com.daepiro.numberoneproject.data.network.onFailure
import com.daepiro.numberoneproject.data.network.onSuccess
import com.daepiro.numberoneproject.domain.usecase.GetAlarmUseCase
import com.daepiro.numberoneproject.presentation.util.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    private val getAlarmUseCase: GetAlarmUseCase
): ViewModel() {
    private val _alarmList = MutableStateFlow<List<Contents>>(emptyList())
    val alarmList = _alarmList.asStateFlow()

    val selectedTab = MutableStateFlow(0)

    fun getAlarmList(isDisaster: Boolean) {
        viewModelScope.launch {
            val token = "Bearer ${tokenManager.accessToken.first()}"

            getAlarmUseCase(token, 0, 10, isDisaster)
                .onSuccess {
                    _alarmList.value = it.content
                }
                .onFailure {

                }
        }
    }
}