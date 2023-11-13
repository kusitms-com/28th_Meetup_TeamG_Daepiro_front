package com.daepiro.numberoneproject.domain.repository

import com.daepiro.numberoneproject.data.model.LoginTestResponse
import com.daepiro.numberoneproject.data.model.LoginTokenResponse
import com.daepiro.numberoneproject.data.model.TokenRequestBody
import com.daepiro.numberoneproject.data.network.ApiResult

interface LoginRepository {
    suspend fun refreshAccessToken(body: TokenRequestBody): ApiResult<LoginTokenResponse>
    suspend fun userNaverLogin(naverLoginBody: TokenRequestBody): ApiResult<LoginTokenResponse>
    suspend fun userKakaoLogin(kakaoLoginBody: TokenRequestBody) : ApiResult<LoginTokenResponse>
    suspend fun testLogin(token: String) : ApiResult<LoginTestResponse>
}