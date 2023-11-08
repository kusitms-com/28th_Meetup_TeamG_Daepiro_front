package com.example.numberoneproject.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.numberoneproject.data.model.ShelterListResponse
import com.example.numberoneproject.data.model.ShelterRequestBody
import com.example.numberoneproject.data.network.onFailure
import com.example.numberoneproject.data.network.onSuccess
import com.example.numberoneproject.domain.usecase.AroundShelterUseCase
import com.example.numberoneproject.presentation.util.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShelterViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    private val aroundShelterUseCase: AroundShelterUseCase
): ViewModel() {
    private val _sheltersList = MutableStateFlow(ShelterListResponse())
    val sheltersList = _sheltersList.asStateFlow()

    val shelterLoadingState = MutableStateFlow(true)

    fun getAroundSheltersList(shelterRequestBody: ShelterRequestBody) {
        viewModelScope.launch {
            val token = "Bearer ${tokenManager.accessToken.first()}"

            aroundShelterUseCase(token, shelterRequestBody)
                .onSuccess {
                    _sheltersList.value = it
                    shelterLoadingState.emit(false)
                }
                .onFailure {

                }
        }
    }

}