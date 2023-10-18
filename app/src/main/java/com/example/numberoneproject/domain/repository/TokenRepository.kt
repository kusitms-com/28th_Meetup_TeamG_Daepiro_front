package com.example.numberoneproject.domain.repository

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


interface TokenRepository {
    suspend fun saveToken(token:List<String>)
    suspend fun getToken(): Flow<List<String>>
    suspend fun getIsLogin(): Flow<Boolean>

    suspend fun setIsLogin(isLoggedIn: Boolean)

}