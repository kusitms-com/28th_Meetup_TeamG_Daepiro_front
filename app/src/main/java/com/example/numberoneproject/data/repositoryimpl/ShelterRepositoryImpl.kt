package com.example.numberoneproject.data.repositoryimpl

import com.example.numberoneproject.data.model.ShelterListResponse
import com.example.numberoneproject.data.model.ShelterRequestBody
import com.example.numberoneproject.data.network.ApiResult
import com.example.numberoneproject.data.network.ApiService
import com.example.numberoneproject.domain.repository.ShelterRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShelterRepositoryImpl @Inject constructor(
    private val service: ApiService
): ShelterRepository {
    override suspend fun getAroundSheltersList(token: String, body: ShelterRequestBody): ApiResult<ShelterListResponse> {
        return service.getAroundSheltersList(token, body)
    }

}