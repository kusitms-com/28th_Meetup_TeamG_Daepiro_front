package com.daepiro.numberoneproject.domain.usecase

import com.daepiro.numberoneproject.data.model.LoginTestResponse
import com.daepiro.numberoneproject.data.network.ApiResult
import com.daepiro.numberoneproject.domain.repository.LoginRepository
import javax.inject.Inject

class TestUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(token: String): ApiResult<LoginTestResponse> {
        return loginRepository.testLogin(token)
    }
}