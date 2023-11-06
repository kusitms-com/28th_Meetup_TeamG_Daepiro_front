package com.example.numberoneproject.domain.repository

import com.example.numberoneproject.data.model.ShelterData
import com.example.numberoneproject.data.network.ApiResult

interface GetShelterRepository {
    suspend fun getShelterDaraLocal(token: String) : ApiResult<List<ShelterData>>
}