package com.example.numberoneproject.domain.usecase

import com.example.numberoneproject.data.model.LoginTestResponse
import com.example.numberoneproject.data.model.LoginTokenResponse
import com.example.numberoneproject.data.model.TokenRequestBody
import com.example.numberoneproject.data.network.ApiResult
import com.example.numberoneproject.domain.repository.LoginRepository
import javax.inject.Inject

class RefreshAccessTokenUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(tokenRequestBody: TokenRequestBody): ApiResult<LoginTokenResponse> {
        return loginRepository.refreshAccessToken(tokenRequestBody)
    }
}