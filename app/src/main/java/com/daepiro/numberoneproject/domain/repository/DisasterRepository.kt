package com.daepiro.numberoneproject.domain.repository

import com.daepiro.numberoneproject.data.model.DisasterRequestBody
import com.daepiro.numberoneproject.data.model.DisasterResponse
import com.daepiro.numberoneproject.data.network.ApiResult

interface DisasterRepository {
    suspend fun getDisasterMessage(token: String,body: DisasterRequestBody): ApiResult<DisasterResponse>
}