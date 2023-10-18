package com.example.numberoneproject.domain.usecase

import com.example.numberoneproject.domain.repository.TokenRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TokenUseCase @Inject constructor(private val tokenRepository: TokenRepository) {
    suspend fun saveToken(token: List<String>){
        tokenRepository.saveToken(token)
    }
    suspend fun getToken() : Flow<List<String>>{
        return tokenRepository.getToken()
    }
    suspend fun getIsLogin():Flow<Boolean>{
        return tokenRepository.getIsLogin()
    }
}