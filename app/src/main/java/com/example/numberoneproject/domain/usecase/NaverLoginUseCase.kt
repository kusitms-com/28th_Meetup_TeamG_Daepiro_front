package com.example.numberoneproject.domain.usecase

import com.example.numberoneproject.data.model.NaverLoginBody
import com.example.numberoneproject.data.model.NaverLoginResponse
import com.example.numberoneproject.data.network.ApiResult
import com.example.numberoneproject.domain.repository.LoginRepository
import javax.inject.Inject

class NaverLoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(
        loginBody: NaverLoginBody
    ): ApiResult<NaverLoginResponse> {
        return loginRepository.userNaverLogin(loginBody)
    }
}