package com.example.numberoneproject.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.numberoneproject.data.model.TokenResponse
import com.example.numberoneproject.data.network.ApiResult
import com.example.numberoneproject.domain.usecase.CheckTokenValidityUseCase
import com.example.numberoneproject.domain.usecase.KakaoLoginUseCase
import com.example.numberoneproject.domain.usecase.TokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KakaoLoginViewModel @Inject constructor(private val getTokenUseCase: KakaoLoginUseCase,
    private val tokenUseCase: TokenUseCase, private val checkTokenValidityUseCase: CheckTokenValidityUseCase)
    :ViewModel(){
    private val _tokenResponse = MutableStateFlow<ApiResult<TokenResponse>?>(null)
    val tokenResponse = _tokenResponse.asStateFlow()

    fun postKakaoToken(token:String){
        viewModelScope.launch {
            val result = getTokenUseCase.postToken(token)
            _tokenResponse.emit(result)
        }
    }

    //토큰 저장로직
    private val _tokenData = MutableStateFlow<List<String>?>(null)
    val tokenData = _tokenData.asStateFlow()

    private val _isLogin = MutableStateFlow<Boolean?>(null)
    val isLogin = _isLogin.asStateFlow()

    //토큰 저장
    fun saveToken(token:List<String>){
        viewModelScope.launch {
            tokenUseCase.saveToken(token)
        }
    }
    fun getToken(){
        viewModelScope.launch {
            tokenUseCase.getToken().collect{tokens->
                _tokenData.value = tokens
            }
        }
    }
    fun checkIsLogin(){
        viewModelScope.launch {
            tokenUseCase.getIsLogin().collect(){isLogin ->
                _isLogin.value = isLogin
            }
        }
    }

    //이를 splash 화면에서 호출해 확인한다
    fun checkTokenValidity(token:String) = viewModelScope.launch {
        try{
            val result = checkTokenValidityUseCase.execute(token)
            if(result != null && result.email.isNotEmpty()){
                //유효
                tokenUseCase.setIsLogin(true)
            }
            else{
                tokenUseCase.setIsLogin(false)
            }
        }catch (e:Exception){
            tokenUseCase.setIsLogin(false)
        }
    }
}