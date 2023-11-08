package com.example.numberoneproject.domain.usecase

import com.example.numberoneproject.data.model.ShelterListResponse
import com.example.numberoneproject.data.model.ShelterRequestBody
import com.example.numberoneproject.data.network.ApiResult
import com.example.numberoneproject.domain.repository.ShelterRepository
import javax.inject.Inject

class AroundShelterUseCase @Inject constructor(
    private val shelterRepository: ShelterRepository
) {
    suspend operator fun invoke(
        token: String,
        body: ShelterRequestBody
    ): ApiResult<ShelterListResponse> {
        return shelterRepository.getAroundSheltersList(token, body)
    }
}