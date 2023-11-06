package com.example.numberoneproject.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.numberoneproject.data.model.ShelterData
import com.example.numberoneproject.data.model.ShelterListResponse
import com.example.numberoneproject.data.model.ShelterRequestBody
import com.example.numberoneproject.data.network.ApiResult
import com.example.numberoneproject.data.network.onFailure
import com.example.numberoneproject.data.network.onSuccess
import com.example.numberoneproject.domain.usecase.AroundShelterUseCase
import com.example.numberoneproject.domain.usecase.GetShelterUseCase
import com.example.numberoneproject.presentation.util.TokenManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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

    fun getSheltersetLocal(){
        viewModelScope.launch {
            val token = "Bearer ${tokenManager.accessToken.first()}"
            val fileName = "shelter_data.json"
            val file = File(filesDir, fileName)
            getShelterUseCase(token,file)
                .onSuccess {
                    Log.d("ShelterViewModel", "성공")
                }
                .onFailure {
                    _url.value = it
                    Log.e("ShelterViewModel", "$it")
                }
        }
    }


}