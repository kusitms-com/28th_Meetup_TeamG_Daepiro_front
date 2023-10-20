package com.example.numberoneproject.data.repositoryimpl

import com.example.numberoneproject.data.model.LoginTestResponse
import com.example.numberoneproject.data.model.LoginTokenResponse
import com.example.numberoneproject.data.model.TokenRequestBody
import com.example.numberoneproject.data.network.ApiResult
import com.example.numberoneproject.data.network.ApiService
import com.example.numberoneproject.domain.repository.LoginRepository
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