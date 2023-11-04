package com.example.numberoneproject.domain.usecase

import com.example.numberoneproject.data.model.ShelterUrlResponse
import com.example.numberoneproject.data.network.ApiResult
import com.example.numberoneproject.domain.repository.GetShelterRepository
import javax.inject.Inject

class GetShelterUseCase @Inject constructor(
    private val getShelterRepository: GetShelterRepository
) {
    suspend operator fun invoke(token:String): ApiResult<ShelterUrlResponse>{
        return getShelterRepository.getShelterUrl(token)
    }
}