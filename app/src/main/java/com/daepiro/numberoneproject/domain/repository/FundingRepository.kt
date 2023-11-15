package com.daepiro.numberoneproject.domain.repository

import com.daepiro.numberoneproject.data.model.CheerMessageRequest
import com.daepiro.numberoneproject.data.model.FundingDetailResponse
import com.daepiro.numberoneproject.data.model.FundingListResponse
import com.daepiro.numberoneproject.data.model.SupportRequest
import com.daepiro.numberoneproject.data.model.SupportResponse
import com.daepiro.numberoneproject.data.model.UserHeartResponse
import com.daepiro.numberoneproject.data.network.ApiResult

interface FundingRepository {
    // 후원 목록 관련
    suspend fun getFundingListByLatest(token: String): ApiResult<FundingListResponse>
    suspend fun getFundingListByPopular(token: String): ApiResult<FundingListResponse>
    suspend fun getFundingDetail(token: String, sponsorId: Int): ApiResult<FundingDetailResponse>

    // 후원관련
    suspend fun postSupport(token: String, body: SupportRequest): ApiResult<SupportResponse>
    suspend fun postCheerMessage(token: String, supportId: Int, body: CheerMessageRequest): ApiResult<Any>

    // 마음 관련
    suspend fun getUserHeartCnt(token: String): ApiResult<UserHeartResponse>
    suspend fun postBuyHeart(token: String, body: UserHeartResponse): ApiResult<UserHeartResponse>
}