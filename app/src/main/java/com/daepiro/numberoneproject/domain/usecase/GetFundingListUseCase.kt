package com.daepiro.numberoneproject.domain.usecase

import com.daepiro.numberoneproject.data.model.FundingListResponse
import com.daepiro.numberoneproject.data.network.ApiResult
import com.daepiro.numberoneproject.domain.repository.FundingRepository
import javax.inject.Inject

class GetFundingListUseCase @Inject constructor(
    private val fundingRepository: FundingRepository
) {
    suspend operator fun invoke(
        token: String,
        sortType: String
    ): ApiResult<FundingListResponse> {
        return if (sortType == "latest") {
            fundingRepository.getFundingListByLatest(token)
        } else {
            fundingRepository.getFundingListByPopular(token)
        }
    }
}