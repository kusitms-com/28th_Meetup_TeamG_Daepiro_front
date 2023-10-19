package com.example.numberoneproject.domain.repository

import com.example.numberoneproject.data.model.LoginTestResponse
import com.example.numberoneproject.data.model.NaverLoginBody
import com.example.numberoneproject.data.model.NaverLoginResponse
import com.example.numberoneproject.data.model.TokenRequestBody
import com.example.numberoneproject.data.network.ApiResult

interface LoginRepository {
    suspend fun refreshAccessToken(body: TokenRequestBody): ApiResult<NaverLoginResponse>
    suspend fun userNaverLogin(naverLoginBody: NaverLoginBody): ApiResult<NaverLoginResponse>
    suspend fun testLogin(token: String) : ApiResult<LoginTestResponse>
}