package com.example.numberoneproject.data.repositoryimpl

import com.example.numberoneproject.data.network.ApiResult
import com.example.numberoneproject.data.network.ApiService
import com.example.numberoneproject.domain.repository.KakaoLoginRepository
import javax.inject.Inject
import javax.inject.Singleton

//@Singleton
//class KakaoLoginRepositoryImpl @Inject constructor(private val service:ApiService): KakaoLoginRepository{
//    override suspend fun sendToken(token:TokenModel): TokenModel?{
//        val response = service.sendToken(token)
//        if(response.isSuccessful){
//            return response.body()
//        }
//        return null
//    }
//}