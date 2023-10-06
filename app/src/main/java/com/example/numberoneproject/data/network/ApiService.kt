package com.example.numberoneproject.data.network

import com.example.numberoneproject.data.model.SampleResponse
import retrofit2.http.GET

interface ApiService {
    @GET("/api/v1/articles")
    suspend fun getQuestions(): ApiResult<SampleResponse>

}