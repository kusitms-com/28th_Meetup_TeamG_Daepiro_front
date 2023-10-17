package com.example.numberoneproject.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.numberoneproject.data.model.NaverLoginBody
import com.example.numberoneproject.data.model.NaverLoginResponse
import com.example.numberoneproject.data.network.ApiResult
import com.example.numberoneproject.data.network.onFailure
import com.example.numberoneproject.data.network.onSuccess
import com.example.numberoneproject.domain.usecase.NaverLoginUseCase
import com.example.numberoneproject.presentation.util.Extensions.myLog
import com.example.numberoneproject.presentation.util.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    application: Application,
    private val naverLoginUseCase: NaverLoginUseCase
) : AndroidViewModel(application) {
    private val tokenManager: TokenManager = TokenManager(application)

    private val _errorState = MutableStateFlow<ApiResult.Failure?>(null)
    val errorState = _errorState.asStateFlow()

    private val _naverLoginResponse = MutableStateFlow(NaverLoginResponse())
    val naverLoginResponse = _naverLoginResponse.asStateFlow()

    private val _naverLoginToken = MutableStateFlow("")
    val naverLoginToken = _naverLoginToken.asStateFlow()

    fun userNaverLogin(loginBody: NaverLoginBody) {
        viewModelScope.launch {
            naverLoginUseCase(loginBody)
                .onSuccess {
                    _naverLoginResponse.value = it

                    writeLoginTokens(it.accessToken, it.refreshToken)
                }
                .onFailure {
                    _errorState.value = it
                }
        }
    }

    fun writeLoginTokens(accessToken: String, refreshToken: String) {
        viewModelScope.launch {
            tokenManager.writeLoginTokens(accessToken, refreshToken)
        }
    }

    fun getLoginTokens() {
        viewModelScope.launch {
            tokenManager.accessToken.collectLatest { _naverLoginToken.value = it }
        }
    }
}