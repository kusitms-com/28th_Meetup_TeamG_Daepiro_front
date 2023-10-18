package com.example.numberoneproject.data.repositoryimpl

import com.example.numberoneproject.data.model.TokenRequest
import com.example.numberoneproject.data.model.TokenResponse
import com.example.numberoneproject.data.network.ApiResult
import com.example.numberoneproject.data.network.ApiService
import com.example.numberoneproject.domain.repository.KakaoLoginRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KakaoLoginRepositoryImpl @Inject constructor(private val service:ApiService): KakaoLoginRepository {
    override suspend fun getToken(token: String): TokenResponse {
        return service.getToken(TokenRequest(token))
    }
}