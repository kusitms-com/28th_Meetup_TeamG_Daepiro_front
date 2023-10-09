package com.example.numberoneproject.data.network

import com.example.numberoneproject.data.model.LoginBody
import com.example.numberoneproject.data.model.LoginResponse
import com.example.numberoneproject.data.model.SampleResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("/api/v1/articles")
    suspend fun getQuestions(): ApiResult<SampleResponse>

    @POST("/login")
    suspend fun userLogin(
        @Body body: LoginBody
    ): ApiResult<LoginResponse>

}