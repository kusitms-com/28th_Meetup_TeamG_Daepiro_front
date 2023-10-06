package com.example.numberoneproject.domain.usecase

import com.example.numberoneproject.data.model.SampleResponse
import com.example.numberoneproject.data.network.ApiResult
import com.example.numberoneproject.domain.repository.SampleRepository
import javax.inject.Inject

class SampleUseCase @Inject constructor(
    private val sampleRepository: SampleRepository
) {
    suspend operator fun invoke(): ApiResult<SampleResponse> {
        return sampleRepository.getSampleApi()
    }
}