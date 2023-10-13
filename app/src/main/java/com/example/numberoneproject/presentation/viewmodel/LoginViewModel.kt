package com.example.numberoneproject.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.numberoneproject.data.model.LoginBody
import com.example.numberoneproject.data.network.onFailure
import com.example.numberoneproject.data.network.onSuccess
import com.example.numberoneproject.domain.usecase.LoginUseCase
import com.example.numberoneproject.presentation.di.MyApplication
import com.example.numberoneproject.presentation.util.Extensions.myLog
import com.example.numberoneproject.presentation.util.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    application: Application,
    private val loginUseCase: LoginUseCase
) : AndroidViewModel(application) {
    private val tokenManager: TokenManager = TokenManager(application)

    fun userLogin(loginBody: LoginBody) {
        viewModelScope.launch {
            loginUseCase(loginBody)
                .onSuccess {

                }
                .onFailure {

                }
        }
    }

    fun setLoginTokens() {
        viewModelScope.launch {
            tokenManager.setTokens("access test", "refresh test")
        }

    }

    fun getLoginTokens() {
        viewModelScope.launch {
            myLog(tokenManager.accessToken.first())
            myLog(tokenManager.refreshToken.first())
        }
    }
}