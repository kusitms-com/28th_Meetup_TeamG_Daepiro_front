package com.example.numberoneproject.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.numberoneproject.data.model.ShelterListResponse
import com.example.numberoneproject.data.model.ShelterRequestBody
import com.example.numberoneproject.data.network.ApiResult
import com.example.numberoneproject.data.network.onFailure
import com.example.numberoneproject.data.network.onSuccess
import com.example.numberoneproject.domain.usecase.AroundShelterUseCase
import com.example.numberoneproject.domain.usecase.GetShelterFromurlUseCase
import com.example.numberoneproject.domain.usecase.GetShelterUseCase
import com.example.numberoneproject.presentation.util.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ShelterViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    private val aroundShelterUseCase: AroundShelterUseCase,
    private val getShelterUseCase: GetShelterUseCase,
    private val getShelterFromurlUseCase: GetShelterFromurlUseCase,
    private val filesDir: File,
): ViewModel() {
    private val _sheltersList = MutableStateFlow(ShelterListResponse())
    val sheltersList = _sheltersList.asStateFlow()

    private val _url = MutableStateFlow<ApiResult.Failure?>(null)
    val url = _url.asStateFlow()

    private val _shelterDataState = MutableLiveData<ApiResult<Unit>>()
    val shelterDataState: LiveData<ApiResult<Unit>> = _shelterDataState

    fun getAroundSheltersList(shelterRequestBody: ShelterRequestBody) {
        viewModelScope.launch {
            val token = "Bearer ${tokenManager.accessToken.first()}"

            aroundShelterUseCase(token, shelterRequestBody)
                .onSuccess {
                    _sheltersList.value = it
                }
                .onFailure {
                    Log.d("taag", it.toString())
                }
        }
    }

    //대피소 조회 url얻기
    fun getShelterUrl(){
        viewModelScope.launch {
            val token = "Bearer ${tokenManager.accessToken.first()}"
            getShelterUseCase(token)
                .onSuccess {
                    Log.d("ShelterViewModel", "${it.link}")
                    val fileName = "shelter_data.json"
                    val file = File(filesDir, fileName)
                    Log.d("ShelterViewModel", "실행전?")
                    saveToFile(it.link,file)
                    Log.d("ShelterViewModel", "실행?")
                }
                .onFailure {
                    _url.value = it
                    Log.d("ShelterViewModel", "${it}")
                }
        }
    }

    fun saveToFile(url:String, file: File){
        viewModelScope.launch {
            try{
                getShelterFromurlUseCase(url,file)
                    .onSuccess {
                        Log.d("ShelterViewModel", "저장 성공")
                    }
                    .onFailure {
                        //현재 이거 반환중
                        Log.e("ShelterViewModel", "저장 실패")
                    }
            }catch (e:Exception){
                Log.e("ShelterViewModel", "savetofile함수 내 에러발생")
            }
        }
    }

}