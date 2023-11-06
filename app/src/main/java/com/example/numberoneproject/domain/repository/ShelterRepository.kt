package com.example.numberoneproject.domain.repository

import com.example.numberoneproject.data.model.ShelterListResponse
import com.example.numberoneproject.data.model.ShelterRequestBody
import com.example.numberoneproject.data.network.ApiResult

interface ShelterRepository {
    suspend fun getAroundSheltersList(token: String,body: ShelterRequestBody): ApiResult<ShelterListResponse>
}