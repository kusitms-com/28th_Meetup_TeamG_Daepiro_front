package com.daepiro.numberoneproject.domain.usecase

import com.daepiro.numberoneproject.data.model.UserHeartResponse
import com.daepiro.numberoneproject.data.network.ApiResult
import com.daepiro.numberoneproject.domain.repository.FundingRepository
import javax.inject.Inject

class GetUserHeartUseCase @Inject constructor(
    private val fundingRepository: FundingRepository
) {
    suspend operator fun invoke(
        token: String,
    ): ApiResult<UserHeartResponse> {
        return fundingRepository.getUserHeartCnt(token)
    }
}