package com.example.numberoneproject.domain.repository

import com.example.numberoneproject.data.model.TokenModel

interface KakaoLoginRepository {
    suspend fun sendToken(token:TokenModel):TokenModel?
}