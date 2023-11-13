package com.daepiro.numberoneproject.data.repositoryimpl

import com.daepiro.numberoneproject.data.model.LoginTestResponse
import com.daepiro.numberoneproject.data.model.LoginTokenResponse
import com.daepiro.numberoneproject.data.model.TokenRequestBody
import com.daepiro.numberoneproject.data.network.ApiResult
import com.daepiro.numberoneproject.data.network.ApiService
import com.daepiro.numberoneproject.domain.repository.LoginRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepositoryImpl @Inject constructor(
    private val service: ApiService,
): LoginRepository {

    override suspend fun refreshAccessToken(tokenRequestBody: TokenRequestBody): ApiResult<LoginTokenResponse> {
        return service.refreshAccessToken(tokenRequestBody)
    }

    override suspend fun userNaverLogin(naverLoginBody: TokenRequestBody): ApiResult<LoginTokenResponse> {
        return service.userNaverLogin(naverLoginBody)
    }

    override suspend fun userKakaoLogin(kakaoLoginBody: TokenRequestBody): ApiResult<LoginTokenResponse>{
        return service.userKakaoLogin(kakaoLoginBody)
    }

    override suspend fun testLogin(token: String): ApiResult<LoginTestResponse> {
        return service.apiLoginTest(token)
    }

}