package com.example.numberoneproject.data.network

import com.example.numberoneproject.data.model.CheckRequest
import com.example.numberoneproject.data.model.SampleResponse
import com.example.numberoneproject.data.model.TokenRequest
import com.example.numberoneproject.data.model.TokenResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @GET("/api/v1/articles")
    suspend fun getQuestions(): ApiResult<SampleResponse>

    @POST("/token/kakao")
    suspend fun getToken(@Body request: TokenRequest): TokenResponse

    //토큰의 유효성 검사
    @GET("/api/logintest")
    suspend fun checkToken(@Header("Authorization") Header:String):Response<CheckRequest>

    @POST("/token/refresh")
    suspend fun getRefreshToken(@Body request: TokenRequest) : TokenRequest

}