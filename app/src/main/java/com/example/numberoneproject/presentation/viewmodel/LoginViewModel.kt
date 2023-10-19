package com.example.numberoneproject.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.numberoneproject.data.model.NaverLoginBody
import com.example.numberoneproject.data.model.NaverLoginResponse
import com.example.numberoneproject.data.model.TokenRequestBody
import com.example.numberoneproject.data.network.ApiResult
import com.example.numberoneproject.data.network.onFailure
import com.example.numberoneproject.data.network.onSuccess
import com.example.numberoneproject.domain.usecase.NaverLoginUseCase
import com.example.numberoneproject.domain.usecase.RefreshAccessTokenUseCase
import com.example.numberoneproject.domain.usecase.TestUseCase
import com.example.numberoneproject.presentation.util.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    application: Application,
    val naverLoginUseCase: NaverLoginUseCase,
    val testLoginUseCase: TestUseCase,
    val refreshAccessTokenUseCase: RefreshAccessTokenUseCase
) : AndroidViewModel(application) {
    private val tokenManager: TokenManager = TokenManager(application)

    private val _errorState = MutableStateFlow<ApiResult.Failure?>(null)
    val errorState = _errorState.asStateFlow()

    fun userNaverLogin(loginBody: NaverLoginBody) {
        viewModelScope.launch {
            naverLoginUseCase(loginBody)
                .onSuccess {
                    tokenManager.writeLoginTokens(it.accessToken, it.refreshToken)
                }
                .onFailure {
                    _errorState.value = it
                }
        }
    }

    fun loginTest() {
        viewModelScope.launch {
            val token = "Bearer ${tokenManager.accessToken.first()}"

            testLoginUseCase(token)
                .onSuccess {
                    Log.d("taag", "1")
                }
                .onFailure {
                    _errorState.value = it
                    Log.d("taag", "2")
                }
        }
    }

    fun refreshAccessToken() {
        viewModelScope.launch {
            val refreshToken = tokenManager.refreshToken.first()

            refreshAccessTokenUseCase(TokenRequestBody(refreshToken))
                .onSuccess {
                    tokenManager.writeLoginTokens(accessToken = it.accessToken, refreshToken = refreshToken)
                    Log.d("taag", "LoginViewModel에서 토큰은 새로 썼음")
                }
                .onFailure {
                    _errorState.value = it
                    Log.d("taag", "AccessToken Refresh 실패")
                }
        }
    }
}