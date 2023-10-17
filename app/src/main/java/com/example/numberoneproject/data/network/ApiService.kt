package com.example.numberoneproject.data.network

import com.example.numberoneproject.data.model.NaverLoginBody
import com.example.numberoneproject.data.model.NaverLoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("/token/naver")
    suspend fun userNaverLogin(
        @Body body: NaverLoginBody
    ): ApiResult<NaverLoginResponse>

}