package com.example.numberoneproject.domain.repository

import com.example.numberoneproject.data.model.CheckRequest

interface CheckTokenRepository {
    suspend fun checkTokenValidity(token:String):CheckRequest
}