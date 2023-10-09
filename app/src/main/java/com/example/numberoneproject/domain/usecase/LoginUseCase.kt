package com.example.numberoneproject.domain.usecase

import com.example.numberoneproject.data.model.LoginBody
import com.example.numberoneproject.data.model.LoginResponse
import com.example.numberoneproject.data.model.SampleResponse
import com.example.numberoneproject.data.network.ApiResult
import com.example.numberoneproject.domain.repository.LoginRepository
import com.example.numberoneproject.domain.repository.SampleRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(
        loginBody: LoginBody
    ): ApiResult<LoginResponse> {
        return loginRepository.userLogin(loginBody)
    }
}