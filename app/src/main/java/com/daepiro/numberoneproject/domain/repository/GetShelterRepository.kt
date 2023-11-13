package com.daepiro.numberoneproject.domain.repository

import com.daepiro.numberoneproject.data.model.ShelterData
import com.daepiro.numberoneproject.data.network.ApiResult

interface GetShelterRepository {
    suspend fun getShelterDaraLocal(token: String) : ApiResult<List<ShelterData>>
}