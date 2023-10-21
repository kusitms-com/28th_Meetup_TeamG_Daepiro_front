package com.example.numberoneproject.data.network

import com.example.numberoneproject.data.model.LoginTestResponse
import com.example.numberoneproject.data.model.LoginTokenResponse
import com.example.numberoneproject.data.model.TokenRequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    // 만료된 AccessToken 갱신
    @POST("/token/refresh")
    suspend fun refreshAccessToken(
        @Body body: TokenRequestBody
    ): ApiResult<LoginTokenResponse>

    // 네이버 로그인
    @POST("/token/naver")
    suspend fun userNaverLogin(
        @Body body: TokenRequestBody
    ): ApiResult<LoginTokenResponse>

    //카카오 로그인
    @POST("/token/kakao")
    suspend fun userKakaoLogin(
        @Body body: TokenRequestBody
    ): ApiResult<LoginTokenResponse>

    // 로그인 테스트
    @GET("/api/logintest")
    suspend fun apiLoginTest(
        @Header("Authorization") token: String
    ): ApiResult<LoginTestResponse>
}