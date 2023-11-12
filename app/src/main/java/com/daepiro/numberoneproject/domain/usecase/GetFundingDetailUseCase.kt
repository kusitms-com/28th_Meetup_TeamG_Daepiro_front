package com.daepiro.numberoneproject.domain.usecase

import com.daepiro.numberoneproject.data.model.FundingDetailResponse
import com.daepiro.numberoneproject.data.network.ApiResult
import com.daepiro.numberoneproject.domain.repository.FundingRepository
import javax.inject.Inject

class GetFundingDetailUseCase @Inject constructor(
    private val fundingRepository: FundingRepository
) {
    suspend operator fun invoke(
        token: String,
        sponsorId: Int
    ): ApiResult<FundingDetailResponse> {
        return fundingRepository.getFundingDetail(token, sponsorId)
    }
}