package com.daepiro.numberoneproject.domain.usecase

import com.daepiro.numberoneproject.data.model.SupportRequest
import com.daepiro.numberoneproject.data.model.SupportResponse
import com.daepiro.numberoneproject.data.network.ApiResult
import com.daepiro.numberoneproject.domain.repository.FundingRepository
import javax.inject.Inject

class SupportUseCase @Inject constructor(
    private val fundingRepository: FundingRepository
) {
    suspend operator fun invoke(
        token: String,
        body: SupportRequest
    ): ApiResult<SupportResponse> {
        return fundingRepository.postSupport(token, body)
    }
}