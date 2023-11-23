package com.daepiro.numberoneproject.data.repositoryimpl

import com.daepiro.numberoneproject.data.model.AlarmResponse
import com.daepiro.numberoneproject.data.model.DisasterRequestBody
import com.daepiro.numberoneproject.data.model.DisasterResponse
import com.daepiro.numberoneproject.data.network.ApiResult
import com.daepiro.numberoneproject.data.network.ApiService
import com.daepiro.numberoneproject.domain.repository.AlarmRepository
import com.daepiro.numberoneproject.domain.repository.DisasterRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlarmRepositoryImpl @Inject constructor(
    private val service: ApiService
): AlarmRepository {
    override suspend fun getAlarmList(
        token: String,
        page: Int,
        size: Int,
        isDisaster: Boolean
    ): ApiResult<AlarmResponse> {
        return service.getAlarmList(token, page, size, isDisaster)
    }
}