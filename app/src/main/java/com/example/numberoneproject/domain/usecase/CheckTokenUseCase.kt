package com.example.numberoneproject.domain.usecase

import com.example.numberoneproject.data.model.CheckRequest
import com.example.numberoneproject.domain.repository.CheckTokenRepository
import javax.inject.Inject

class CheckTokenValidityUseCase @Inject constructor(private val repository: CheckTokenRepository) {
    suspend fun execute(token: String): CheckRequest {
        return repository.checkTokenValidity(token)
    }
}