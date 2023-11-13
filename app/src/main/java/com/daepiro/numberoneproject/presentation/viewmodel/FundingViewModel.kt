package com.daepiro.numberoneproject.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daepiro.numberoneproject.data.model.FundingDetailResponse
import com.daepiro.numberoneproject.data.model.FundingListResponse
import com.daepiro.numberoneproject.data.network.onFailure
import com.daepiro.numberoneproject.data.network.onSuccess
import com.daepiro.numberoneproject.domain.usecase.GetFundingDetailUseCase
import com.daepiro.numberoneproject.domain.usecase.GetFundingListUseCase
import com.daepiro.numberoneproject.presentation.util.TokenManager
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
    // 후원 목록
    private val _fundingList = MutableStateFlow(FundingListResponse())
    val fundingList = _fundingList.asStateFlow()

    // 후원 상세정보
    private val _fundingDetail = MutableStateFlow(FundingDetailResponse())
    val fundingDetail = _fundingDetail.asStateFlow()

    // 후원 관련 화면 로딩상태
    val fundingListLoadingState = MutableStateFlow(true)
    val fundingDetailLoadingState = MutableStateFlow(true)

    // 후원 바텀싯
    val selectedHeartCount = MutableStateFlow(0)

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