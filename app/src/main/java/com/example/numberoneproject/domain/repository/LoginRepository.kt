package com.example.numberoneproject.domain.repository

import com.example.numberoneproject.data.model.LoginTestResponse
import com.example.numberoneproject.data.model.LoginTokenResponse
import com.example.numberoneproject.data.model.TokenRequestBody
import com.example.numberoneproject.data.network.ApiResult

interface LoginRepository {
    suspend fun refreshAccessToken(body: TokenRequestBody): ApiResult<LoginTokenResponse>
    suspend fun userNaverLogin(naverLoginBody: TokenRequestBody): ApiResult<LoginTokenResponse>
    suspend fun testLogin(token: String) : ApiResult<LoginTestResponse>
}