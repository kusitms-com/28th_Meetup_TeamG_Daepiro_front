package com.example.numberoneproject.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.numberoneproject.data.model.DisasterRequestBody
import com.example.numberoneproject.data.model.DisasterResponse
import com.example.numberoneproject.data.model.ShelterListResponse
import com.example.numberoneproject.data.network.onFailure
import com.example.numberoneproject.data.network.onSuccess
import com.example.numberoneproject.domain.usecase.AroundShelterUseCase
import com.example.numberoneproject.domain.usecase.DisasterMessageUseCase
import com.example.numberoneproject.presentation.util.TokenManager
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

    fun getDisasterMessage(disasterRequestBody: DisasterRequestBody) {
        viewModelScope.launch {
            val token = "Bearer ${tokenManager.accessToken.first()}"

            disasterMessageUseCase(token, disasterRequestBody)
                .onSuccess {
                    _disasterMessage.value = it
                }
                .onFailure {

                }
        }
    }

}