package com.daepiro.numberoneproject.domain.usecase

import com.daepiro.numberoneproject.data.model.LoginTokenResponse
import com.daepiro.numberoneproject.data.model.TokenRequestBody
import com.daepiro.numberoneproject.data.network.ApiResult
import com.daepiro.numberoneproject.domain.repository.LoginRepository
import javax.inject.Inject

class RefreshAccessTokenUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(tokenRequestBody: TokenRequestBody): ApiResult<LoginTokenResponse> {
        return loginRepository.refreshAccessToken(tokenRequestBody)
    }
}