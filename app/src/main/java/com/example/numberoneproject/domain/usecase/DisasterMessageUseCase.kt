package com.example.numberoneproject.domain.usecase

import com.example.numberoneproject.data.model.DisasterRequestBody
import com.example.numberoneproject.data.model.DisasterResponse
import com.example.numberoneproject.data.model.ShelterListResponse
import com.example.numberoneproject.data.model.ShelterRequestBody
import com.example.numberoneproject.data.network.ApiResult
import com.example.numberoneproject.domain.repository.DisasterRepository
import com.example.numberoneproject.domain.repository.ShelterRepository
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