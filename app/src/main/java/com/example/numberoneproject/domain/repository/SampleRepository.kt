package com.example.numberoneproject.domain.repository

import com.example.numberoneproject.data.model.SampleResponse
import com.example.numberoneproject.data.network.ApiResult

interface SampleRepository {
    suspend fun getSampleApi(): ApiResult<SampleResponse>
}