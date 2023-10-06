package com.example.numberoneproject.data.repositoryimpl

import com.example.numberoneproject.data.model.SampleResponse
import com.example.numberoneproject.data.network.ApiResult
import com.example.numberoneproject.data.network.ApiService
import com.example.numberoneproject.domain.repository.SampleRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SampleRepositoryImpl @Inject constructor(
    private val service: ApiService
): SampleRepository {
    override suspend fun getSampleApi(): ApiResult<SampleResponse> {
        return service.getQuestions()
    }
}