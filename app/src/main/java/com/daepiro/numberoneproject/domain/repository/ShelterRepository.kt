package com.daepiro.numberoneproject.domain.repository

import com.daepiro.numberoneproject.data.model.ShelterListResponse
import com.daepiro.numberoneproject.data.model.ShelterRequestBody
import com.daepiro.numberoneproject.data.network.ApiResult

interface ShelterRepository {
    suspend fun getAroundSheltersList(token: String,body: ShelterRequestBody): ApiResult<ShelterListResponse>
}