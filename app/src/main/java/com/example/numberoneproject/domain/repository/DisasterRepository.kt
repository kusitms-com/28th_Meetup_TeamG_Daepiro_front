package com.example.numberoneproject.domain.repository

import com.example.numberoneproject.data.model.DisasterRequestBody
import com.example.numberoneproject.data.model.DisasterResponse
import com.example.numberoneproject.data.model.ShelterRequestBody
import com.example.numberoneproject.data.network.ApiResult

interface DisasterRepository {
    suspend fun getDisasterMessage(token: String,body: DisasterRequestBody): ApiResult<DisasterResponse>
}