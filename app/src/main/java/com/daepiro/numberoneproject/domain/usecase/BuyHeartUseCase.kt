package com.daepiro.numberoneproject.domain.usecase

import com.daepiro.numberoneproject.data.model.UserHeartResponse
import com.daepiro.numberoneproject.data.network.ApiResult
import com.daepiro.numberoneproject.domain.repository.FundingRepository
import javax.inject.Inject

class BuyHeartUseCase @Inject constructor(
    private val fundingRepository: FundingRepository
) {
    suspend operator fun invoke(
        token: String,
        body: UserHeartResponse
    ): ApiResult<UserHeartResponse> {
        return fundingRepository.postBuyHeart(token, body)
    }
}