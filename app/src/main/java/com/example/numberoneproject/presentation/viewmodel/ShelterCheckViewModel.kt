package com.example.numberoneproject.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.numberoneproject.data.network.ApiResult
import com.example.numberoneproject.data.network.onFailure
import com.example.numberoneproject.data.network.onSuccess
import com.example.numberoneproject.domain.usecase.GetShelterUseCase
import com.example.numberoneproject.presentation.util.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShelterCheckViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    private val getShelterUseCase: GetShelterUseCase,
): ViewModel() {
    private val _url = MutableStateFlow<ApiResult.Failure?>(null)
    val url = _url.asStateFlow()

    fun getUrl(){
        viewModelScope.launch {
            val token = "Bearer ${tokenManager.accessToken.first()}"
            getShelterUseCase(token)
                .onSuccess {

                }
                .onFailure {
                    _url.value = it
                }
        }
    }
}