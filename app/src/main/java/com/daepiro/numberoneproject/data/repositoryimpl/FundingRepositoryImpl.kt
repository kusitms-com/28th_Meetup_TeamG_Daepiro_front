package com.daepiro.numberoneproject.data.repositoryimpl

import com.daepiro.numberoneproject.data.model.CheerMessageRequest
import com.daepiro.numberoneproject.data.model.FundingDetailResponse
import com.daepiro.numberoneproject.data.model.FundingListResponse
import com.daepiro.numberoneproject.data.model.SupportRequest
import com.daepiro.numberoneproject.data.model.SupportResponse
import com.daepiro.numberoneproject.data.model.UserHeartResponse
import com.daepiro.numberoneproject.data.network.ApiResult
import com.daepiro.numberoneproject.data.network.ApiService
import com.daepiro.numberoneproject.domain.repository.FundingRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FundingRepositoryImpl @Inject constructor(
    private val service: ApiService
): FundingRepository {
    // 후원 목록 관련
    override suspend fun getFundingListByLatest(token: String): ApiResult<FundingListResponse> {
        return service.getFundingListByLatest(token)
    }

    override suspend fun getFundingListByPopular(token: String): ApiResult<FundingListResponse> {
        return service.getFundingListByPopular(token)
    }

    override suspend fun getFundingDetail(token: String, sponsorId: Int): ApiResult<FundingDetailResponse> {
        return service.getFundingDetail(token, sponsorId)
    }

    // 후원관련
    override suspend fun postSupport(token: String, body: SupportRequest): ApiResult<SupportResponse> {
        return service.postSupport(token, body)
    }

    override suspend fun postCheerMessage(token: String, supportId: Int, body: CheerMessageRequest): ApiResult<Any> {
        return service.postCheerMessage(token, supportId, body)
    }

    // 마음 관련
    override suspend fun getUserHeartCnt(token: String): ApiResult<UserHeartResponse> {
        return service.getUserHeartCnt(token)
    }

    override suspend fun postBuyHeart(token: String, body: UserHeartResponse): ApiResult<UserHeartResponse> {
        return service.postBuyHeart(token, body)
    }

}