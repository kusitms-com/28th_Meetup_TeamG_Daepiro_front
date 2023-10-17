package com.example.numberoneproject.domain.repository

import com.example.numberoneproject.data.model.NaverLoginBody
import com.example.numberoneproject.data.model.NaverLoginResponse
import com.example.numberoneproject.data.network.ApiResult

interface LoginRepository {
    suspend fun userNaverLogin(naverLoginBody: NaverLoginBody): ApiResult<NaverLoginResponse>
}