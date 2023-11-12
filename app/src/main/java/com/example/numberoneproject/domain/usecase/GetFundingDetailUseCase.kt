package com.example.numberoneproject.domain.usecase

import com.example.numberoneproject.data.model.FundingDetailResponse
import com.example.numberoneproject.data.model.FundingListResponse
import com.example.numberoneproject.data.model.ShelterListResponse
import com.example.numberoneproject.data.model.ShelterRequestBody
import com.example.numberoneproject.data.network.ApiResult
import com.example.numberoneproject.domain.repository.FundingRepository
import com.example.numberoneproject.domain.repository.ShelterRepository
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