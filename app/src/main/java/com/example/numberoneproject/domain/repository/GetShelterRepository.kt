package com.example.numberoneproject.domain.repository

import com.example.numberoneproject.data.model.ShelterData
import com.example.numberoneproject.data.model.ShelterUrlResponse
import com.example.numberoneproject.data.network.ApiResult

interface GetShelterRepository {
    suspend fun getShelter(token: String) : ApiResult<ShelterUrlResponse>
    suspend fun fetchShelterFromUrl(url: String): ApiResult<ShelterData>

}