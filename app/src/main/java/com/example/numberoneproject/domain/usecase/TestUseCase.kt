package com.example.numberoneproject.domain.usecase

import com.example.numberoneproject.data.model.LoginTestResponse
import com.example.numberoneproject.data.network.ApiResult
import com.example.numberoneproject.domain.repository.LoginRepository
import javax.inject.Inject

class TestUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(token: String): ApiResult<LoginTestResponse> {
        return loginRepository.testLogin(token)
    }
}