package com.daepiro.numberoneproject.domain.repository

import com.daepiro.numberoneproject.data.model.FundingDetailResponse
import com.daepiro.numberoneproject.data.model.FundingListResponse
import com.daepiro.numberoneproject.data.network.ApiResult

interface FundingRepository {
    suspend fun getFundingListByLatest(token: String): ApiResult<FundingListResponse>
    suspend fun getFundingListByPopular(token: String): ApiResult<FundingListResponse>
    suspend fun getFundingDetail(token: String, sponsorId: Int): ApiResult<FundingDetailResponse>
}