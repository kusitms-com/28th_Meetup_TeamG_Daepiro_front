package com.example.numberoneproject.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.numberoneproject.data.model.DisasterResponse
import com.example.numberoneproject.data.model.FundingDetailResponse
import com.example.numberoneproject.data.model.FundingListResponse
import com.example.numberoneproject.data.network.onFailure
import com.example.numberoneproject.data.network.onSuccess
import com.example.numberoneproject.domain.usecase.DisasterMessageUseCase
import com.example.numberoneproject.domain.usecase.GetFundingDetailUseCase
import com.example.numberoneproject.domain.usecase.GetFundingListUseCase
import com.example.numberoneproject.presentation.util.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FundingViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    private val getFundingListUseCase: GetFundingListUseCase,
    private val getFundingDetailUseCase: GetFundingDetailUseCase
): ViewModel() {
    private val _fundingList = MutableStateFlow(FundingListResponse())
    val fundingList = _fundingList.asStateFlow()

    private val _fundingDetail = MutableStateFlow(FundingDetailResponse())
    val fundingDetail = _fundingDetail.asStateFlow()

    val fundingListLoadingState = MutableStateFlow(true)
    val fundingDetailLoadingState = MutableStateFlow(true)

    fun getFundingList(sortType: String) {
        viewModelScope.launch {
            val token = "Bearer ${tokenManager.accessToken.first()}"

            getFundingListUseCase(token, sortType)
                .onSuccess {
                    fundingListLoadingState.emit(false)
                    _fundingList.value = it
                }
                .onFailure {

                }
        }
    }

    fun getFundingDetail(sponsorId: Int) {
        viewModelScope.launch {
            val token = "Bearer ${tokenManager.accessToken.first()}"

            getFundingDetailUseCase(token, sponsorId)
                .onSuccess {
                    fundingDetailLoadingState.emit(false)
                    _fundingDetail.value = it
                }
                .onFailure {

                }
        }
    }
}