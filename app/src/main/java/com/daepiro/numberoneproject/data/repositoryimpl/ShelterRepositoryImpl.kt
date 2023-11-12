package com.daepiro.numberoneproject.data.repositoryimpl

import com.daepiro.numberoneproject.data.model.ShelterListResponse
import com.daepiro.numberoneproject.data.model.ShelterRequestBody
import com.daepiro.numberoneproject.data.network.ApiResult
import com.daepiro.numberoneproject.data.network.ApiService
import com.daepiro.numberoneproject.domain.repository.ShelterRepository
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