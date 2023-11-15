package com.daepiro.numberoneproject.domain.usecase

import com.daepiro.numberoneproject.data.model.CheerMessageRequest
import com.daepiro.numberoneproject.data.model.SupportRequest
import com.daepiro.numberoneproject.data.model.SupportResponse
import com.daepiro.numberoneproject.data.network.ApiResult
import com.daepiro.numberoneproject.domain.repository.FundingRepository
import javax.inject.Inject

class PostCheerMessageUseCase @Inject constructor(
    private val fundingRepository: FundingRepository
) {
    suspend operator fun invoke(
        token: String,
        supportId: Int,
        body: CheerMessageRequest
    ): ApiResult<Any> {
        return fundingRepository.postCheerMessage(token, supportId, body)
    }
}