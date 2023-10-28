package com.example.numberoneproject.data.repositoryimpl

import com.example.numberoneproject.data.model.ShelterData
import com.example.numberoneproject.data.model.ShelterUrlResponse
import com.example.numberoneproject.data.network.ApiResult
import com.example.numberoneproject.data.network.ApiService
import com.example.numberoneproject.domain.repository.GetShelterRepository
import javax.inject.Inject

class GetShelterRepositoryImpl @Inject constructor(
    private val service: ApiService,
): GetShelterRepository {
    override suspend fun getShelter(token: String): ApiResult<ShelterUrlResponse>{
        return service.getShelters(token)
    }

    override suspend fun fetchShelterFromUrl(url: String): ApiResult<ShelterData> {
        return service.getDataFromUrl(url)
    }
}