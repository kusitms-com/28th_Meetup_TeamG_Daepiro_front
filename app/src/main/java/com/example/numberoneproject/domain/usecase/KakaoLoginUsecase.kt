package com.example.numberoneproject.domain.usecase

import com.example.numberoneproject.data.model.LoginTokenResponse
import com.example.numberoneproject.data.model.TokenRequestBody
import com.example.numberoneproject.data.network.ApiResult
import com.example.numberoneproject.domain.repository.LoginRepository
import javax.inject.Inject
class KakaoLoginUsecase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(
        loginBody: TokenRequestBody
    ) : ApiResult<LoginTokenResponse>{
        return loginRepository.userKakaoLogin(loginBody)
    }
}