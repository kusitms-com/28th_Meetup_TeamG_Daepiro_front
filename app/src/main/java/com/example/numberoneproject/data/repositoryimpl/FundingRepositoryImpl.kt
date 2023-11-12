package com.example.numberoneproject.data.repositoryimpl

import com.example.numberoneproject.data.model.DisasterRequestBody
import com.example.numberoneproject.data.model.DisasterResponse
import com.example.numberoneproject.data.model.FundingDetailResponse
import com.example.numberoneproject.data.model.FundingListResponse
import com.example.numberoneproject.data.network.ApiResult
import com.example.numberoneproject.data.network.ApiService
import com.example.numberoneproject.domain.repository.DisasterRepository
import com.example.numberoneproject.domain.repository.FundingRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FundingRepositoryImpl @Inject constructor(
    private val service: ApiService
): FundingRepository {
    override suspend fun getFundingListByLatest(token: String): ApiResult<FundingListResponse> {
        return service.getFundingListByLatest(token)
    }

    override suspend fun getFundingListByPopular(token: String): ApiResult<FundingListResponse> {
        return service.getFundingListByPopular(token)
    }

    override suspend fun getFundingDetail(token: String, sponsorId: Int): ApiResult<FundingDetailResponse> {
        return service.getFundingDetail(token, sponsorId)
    }

}