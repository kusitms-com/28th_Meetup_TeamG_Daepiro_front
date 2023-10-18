package com.example.numberoneproject.data.repositoryimpl

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.numberoneproject.data.model.TokenRequest
import com.example.numberoneproject.data.model.TokenResponse
import com.example.numberoneproject.data.network.ApiResult
import com.example.numberoneproject.data.network.ApiService
import com.example.numberoneproject.domain.repository.KakaoLoginRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KakaoLoginRepositoryImpl @Inject constructor(private val service:ApiService): KakaoLoginRepository {
    override suspend fun getToken(token: String): TokenResponse {
        return service.getToken(TokenRequest(token))
    }


}