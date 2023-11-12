package com.example.numberoneproject.domain.repository

import com.example.numberoneproject.data.model.FundingDetailResponse
import com.example.numberoneproject.data.model.FundingListResponse
import com.example.numberoneproject.data.network.ApiResult

interface FundingRepository {
    suspend fun getFundingListByLatest(token: String): ApiResult<FundingListResponse>
    suspend fun getFundingListByPopular(token: String): ApiResult<FundingListResponse>
    suspend fun getFundingDetail(token: String, sponsorId: Int): ApiResult<FundingDetailResponse>
}