package com.example.numberoneproject.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.numberoneproject.data.model.TokenResponse
import com.example.numberoneproject.data.network.ApiResult
import com.example.numberoneproject.domain.usecase.KakaoLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KakaoLoginViewModel @Inject constructor(private val getTokenUseCase: KakaoLoginUseCase)
    :ViewModel(){
    private val _tokenResponse = MutableStateFlow<ApiResult<TokenResponse>?>(null)
    val tokenResponse = _tokenResponse.asStateFlow()


    fun postKakaoToken(token:String){
        viewModelScope.launch {
            val result = getTokenUseCase.postToken(token)
            _tokenResponse.emit(result)
        }
    }
}