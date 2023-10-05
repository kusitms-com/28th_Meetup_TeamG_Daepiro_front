package com.example.numberoneproject.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.numberoneproject.data.model.SampleResponse
import com.example.numberoneproject.data.network.ApiResult
import com.example.numberoneproject.data.network.onFailure
import com.example.numberoneproject.data.network.onSuccess
import com.example.numberoneproject.domain.usecase.SampleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SampleViewModel @Inject constructor(
    private val sampleUseCase: SampleUseCase
): ViewModel() {
    private val _sampleResponse = MutableStateFlow(SampleResponse())
    val sampleResponse = _sampleResponse.asStateFlow()

    private val _errorState = MutableStateFlow<ApiResult.Failure?>(null)
    val errorState = _errorState.asStateFlow()

    fun getSample() {
        viewModelScope.launch {
            sampleUseCase()
                .onSuccess {
                    _sampleResponse.value = it
                }
                .onFailure {
                    _errorState.value = it
                }
        }
    }
}