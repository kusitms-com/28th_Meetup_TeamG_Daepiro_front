package com.daepiro.numberoneproject.domain.usecase

import com.daepiro.numberoneproject.data.model.DisasterRequestBody
import com.daepiro.numberoneproject.data.model.DisasterResponse
import com.daepiro.numberoneproject.data.network.ApiResult
import com.daepiro.numberoneproject.domain.repository.DisasterRepository
import javax.inject.Inject

class DisasterMessageUseCase @Inject constructor(
    private val disasterRepository: DisasterRepository
) {
    suspend operator fun invoke(
        token: String,
        body: DisasterRequestBody
    ): ApiResult<DisasterResponse> {
        return disasterRepository.getDisasterMessage(token, body)
    }
}