package com.daepiro.numberoneproject.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daepiro.numberoneproject.data.model.CheerMessageRequest
import com.daepiro.numberoneproject.data.model.FundingDetailResponse
import com.daepiro.numberoneproject.data.model.FundingListResponse
import com.daepiro.numberoneproject.data.model.SupportRequest
import com.daepiro.numberoneproject.data.model.UserHeartResponse
import com.daepiro.numberoneproject.data.network.onFailure
import com.daepiro.numberoneproject.data.network.onSuccess
import com.daepiro.numberoneproject.domain.usecase.BuyHeartUseCase
import com.daepiro.numberoneproject.domain.usecase.GetFundingDetailUseCase
import com.daepiro.numberoneproject.domain.usecase.GetFundingListUseCase
import com.daepiro.numberoneproject.domain.usecase.GetUserHeartUseCase
import com.daepiro.numberoneproject.domain.usecase.PostCheerMessageUseCase
import com.daepiro.numberoneproject.domain.usecase.SupportUseCase
import com.daepiro.numberoneproject.presentation.util.TokenManager
import com.daepiro.numberoneproject.presentation.view.funding.detail.HeartChargeFragment
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
    private val getFundingDetailUseCase: GetFundingDetailUseCase,
    private val getUserHeartUseCase: GetUserHeartUseCase,
    private val supportUseCase: SupportUseCase,
    private val postCheerMessageUseCase: PostCheerMessageUseCase,
    private val buyHeartUseCase: BuyHeartUseCase
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

    // 후원 결과 저장
    val supportResult = MutableStateFlow(-1)


    private val _heartCnt = MutableStateFlow(0)    // 마음 갯수
    val heartCnt = _heartCnt.asStateFlow()

    val selectedHeartCount = MutableStateFlow(0)    // 유저가 선택한 마음 갯수

    // 후원 목록 조회
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

    // 후원 상세정보 조회
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

    // 사용자 마음 갯수 조회
    fun getUserHeartCnt() {
        viewModelScope.launch {
            val token = "Bearer ${tokenManager.accessToken.first()}"

            getUserHeartUseCase(token)
                .onSuccess {
                    _heartCnt.value = it.heartCnt
                }
                .onFailure {

                }
        }
    }

    // 후원하기
    fun postSupport(body: SupportRequest) {
        viewModelScope.launch {
            val token = "Bearer ${tokenManager.accessToken.first()}"

            supportUseCase(token, body)
                .onSuccess {
                    supportResult.value = it.supportId
                }
                .onFailure {

                }
        }
    }

    // 응원메시지 보내기
    fun postCheerMessage(supportId: Int, body: CheerMessageRequest) {
        viewModelScope.launch {
            val token = "Bearer ${tokenManager.accessToken.first()}"

            postCheerMessageUseCase(token, supportId, body)
                .onSuccess {

                }
                .onFailure {

                }
        }
    }

    // 마음 구매
    fun buyHeart(body: UserHeartResponse) {
        viewModelScope.launch {
            val token = "Bearer ${tokenManager.accessToken.first()}"

            buyHeartUseCase(token, body)
                .onSuccess {

                }
                .onFailure {

                }
        }
    }
}