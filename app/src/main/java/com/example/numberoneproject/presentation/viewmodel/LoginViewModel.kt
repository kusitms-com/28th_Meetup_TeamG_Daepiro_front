package com.example.numberoneproject.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.numberoneproject.data.model.LoginBody
import com.example.numberoneproject.data.network.onFailure
import com.example.numberoneproject.data.network.onSuccess
import com.example.numberoneproject.domain.usecase.LoginUseCase
import com.example.numberoneproject.presentation.util.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    fun userLogin(loginBody: LoginBody) {
        viewModelScope.launch {
            loginUseCase(loginBody)
                .onSuccess {

                }
                .onFailure {

                }
        }
    }

    fun setLoginTokens(tokenManager: TokenManager) {
        viewModelScope.launch {
            tokenManager.setTokens("access test", "refresh test")
        }

    }
}