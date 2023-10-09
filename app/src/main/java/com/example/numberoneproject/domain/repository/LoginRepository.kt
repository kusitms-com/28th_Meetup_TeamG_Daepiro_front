package com.example.numberoneproject.domain.repository

import com.example.numberoneproject.data.model.LoginBody
import com.example.numberoneproject.data.model.LoginResponse
import com.example.numberoneproject.data.network.ApiResult

interface LoginRepository {
    suspend fun userLogin(loginBody: LoginBody): ApiResult<LoginResponse>
}