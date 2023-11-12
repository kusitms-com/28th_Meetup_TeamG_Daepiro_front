package com.daepiro.numberoneproject.domain.usecase

import com.daepiro.numberoneproject.data.model.ShelterListResponse
import com.daepiro.numberoneproject.data.model.ShelterRequestBody
import com.daepiro.numberoneproject.data.network.ApiResult
import com.daepiro.numberoneproject.domain.repository.ShelterRepository
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