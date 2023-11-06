package com.example.numberoneproject.data.repositoryimpl

import com.example.numberoneproject.data.model.ShelterData
import com.example.numberoneproject.data.network.ApiResult
import com.example.numberoneproject.data.network.ApiService
import com.example.numberoneproject.domain.repository.GetShelterRepository
import javax.inject.Inject

class GetShelterRepositoryImpl @Inject constructor(
    private val service: ApiService,
): GetShelterRepository {
    override suspend fun getShelterDaraLocal(token: String): ApiResult<List<ShelterData>>{
        return service.getShelters(token)
    }

}