package com.example.numberoneproject.data.repositoryimpl

import com.example.numberoneproject.data.model.DisasterRequestBody
import com.example.numberoneproject.data.model.DisasterResponse
import com.example.numberoneproject.data.network.ApiResult
import com.example.numberoneproject.data.network.ApiService
import com.example.numberoneproject.domain.repository.DisasterRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DisasterRepositoryImpl @Inject constructor(
    private val service: ApiService
): DisasterRepository {
    override suspend fun getDisasterMessage(token: String, body: DisasterRequestBody): ApiResult<DisasterResponse> {
        return service.getDisasterMessage(token, body)
    }
}