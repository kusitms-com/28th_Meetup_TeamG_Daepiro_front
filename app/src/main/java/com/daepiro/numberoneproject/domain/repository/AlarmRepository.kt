package com.daepiro.numberoneproject.domain.repository

import com.daepiro.numberoneproject.data.model.AlarmResponse
import com.daepiro.numberoneproject.data.model.DisasterRequestBody
import com.daepiro.numberoneproject.data.model.DisasterResponse
import com.daepiro.numberoneproject.data.network.ApiResult

interface AlarmRepository {
    suspend fun getAlarmList(token: String, page: Int, size: Int, isDisaster: Boolean): ApiResult<AlarmResponse>
}