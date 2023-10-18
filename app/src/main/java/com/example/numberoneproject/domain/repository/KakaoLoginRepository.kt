package com.example.numberoneproject.domain.repository

import com.example.numberoneproject.data.model.TokenResponse


interface KakaoLoginRepository {
    suspend fun getToken(token:String):TokenResponse
}