package com.example.numberoneproject.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.numberoneproject.data.model.TokenRequestBody
import com.example.numberoneproject.data.network.ApiResult
import com.example.numberoneproject.data.network.onFailure
import com.example.numberoneproject.data.network.onSuccess
import com.example.numberoneproject.domain.usecase.KakaoLoginUsecase
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
    private val tokenManager: TokenManager,
    private val naverLoginUseCase: NaverLoginUseCase,
    private val kakaoLoginUsecase: KakaoLoginUsecase,
    private val testLoginUseCase: TestUseCase,
    private val refreshAccessTokenUseCase: RefreshAccessTokenUseCase
) : ViewModel() {

    private val _loginErrorState = MutableStateFlow<ApiResult.Failure?>(null)
    val loginErrorState = _loginErrorState.asStateFlow()

    fun userNaverLogin(loginBody: TokenRequestBody) {
        viewModelScope.launch {
            naverLoginUseCase(loginBody)
                .onSuccess {
                    tokenManager.writeLoginTokens(it.accessToken, it.refreshToken)
                }
                .onFailure {
                    _loginErrorState.value = it
                }
        }
    }

    fun userKakaoLogin(loginBody: TokenRequestBody) {
        viewModelScope.launch {
            kakaoLoginUsecase(loginBody)
                .onSuccess {
                    tokenManager.writeLoginTokens(it.accessToken, it.refreshToken)
                }
                .onFailure {
                    _loginErrorState.value = it
                }
        }
    }

    /** loginTest 관련 코드는 지금은 테스트 용도고 금방 지워질 코드 **/
    fun loginTest() {
        viewModelScope.launch {
            val token = "Bearer ${tokenManager.accessToken.first()}"

            testLoginUseCase(token)
                .onSuccess {
                    Log.d("taag", "1")
                }
                .onFailure {
                    _loginErrorState.value = it
                    Log.d("taag", "2")
                }
        }
    }

    fun refreshAccessToken() {
        viewModelScope.launch {
            val refreshToken = tokenManager.refreshToken.first()

            refreshAccessTokenUseCase(TokenRequestBody(refreshToken))
                .onSuccess {
                    tokenManager.writeLoginTokens(accessToken = it.accessToken, refreshToken = it.refreshToken)
                    Log.d("taag", "LoginViewModel에서 토큰은 새로 썼음")
                }
                .onFailure {
                    _loginErrorState.value = it
                    Log.d("taag", "AccessToken Refresh 실패")
                }
        }
    }
}