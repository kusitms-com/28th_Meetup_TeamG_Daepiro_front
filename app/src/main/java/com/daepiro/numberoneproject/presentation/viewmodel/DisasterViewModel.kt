package com.daepiro.numberoneproject.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daepiro.numberoneproject.data.model.DisasterRequestBody
import com.daepiro.numberoneproject.data.model.DisasterResponse
import com.daepiro.numberoneproject.data.network.onFailure
import com.daepiro.numberoneproject.data.network.onSuccess
import com.daepiro.numberoneproject.domain.usecase.DisasterMessageUseCase
import com.daepiro.numberoneproject.presentation.util.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DisasterViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    private val disasterMessageUseCase: DisasterMessageUseCase
): ViewModel() {
    private val _disasterMessage = MutableStateFlow(DisasterResponse())
    val disasterMessage = _disasterMessage.asStateFlow()

    val disasterLoadingState = MutableStateFlow(true)

    val checkListIsExpanded = MutableStateFlow(false)

    fun getDisasterMessage(disasterRequestBody: DisasterRequestBody) {
        _disasterMessage.value = DisasterResponse()
        disasterLoadingState.value = true

        viewModelScope.launch {
            val token = "Bearer ${tokenManager.accessToken.first()}"

            disasterMessageUseCase(token, disasterRequestBody)
                .onSuccess {
                    _disasterMessage.value = it
                    disasterLoadingState.value = false
                }
                .onFailure {

                }
        }
    }

    fun changeExpandedState() {
        viewModelScope.launch {
            checkListIsExpanded.value = !checkListIsExpanded.value
        }
    }

}