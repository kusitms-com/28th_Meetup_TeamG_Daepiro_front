package com.daepiro.numberoneproject.data.repositoryimpl

import com.daepiro.numberoneproject.data.model.DisasterRequestBody
import com.daepiro.numberoneproject.data.model.DisasterResponse
import com.daepiro.numberoneproject.data.network.ApiResult
import com.daepiro.numberoneproject.data.network.ApiService
import com.daepiro.numberoneproject.domain.repository.DisasterRepository
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