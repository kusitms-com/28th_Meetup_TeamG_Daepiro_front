package com.example.numberoneproject.domain.usecase

import com.example.numberoneproject.data.model.FundingListResponse
import com.example.numberoneproject.data.model.ShelterListResponse
import com.example.numberoneproject.data.model.ShelterRequestBody
import com.example.numberoneproject.data.network.ApiResult
import com.example.numberoneproject.domain.repository.FundingRepository
import com.example.numberoneproject.domain.repository.ShelterRepository
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