package com.example.numberoneproject.presentation.util

import android.content.Context
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.numberoneproject.presentation.util.TokenManager.Companion.TOKEN_DATA_STORE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.tokenDataStore by preferencesDataStore(name = TOKEN_DATA_STORE)

class TokenManager(private val context: Context) {

    companion object {
        private val ACCESS_TOKEN = stringPreferencesKey("access_token")
        private val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
        const val TOKEN_DATA_STORE = "token_data_store"
    }

    suspend fun setTokens(accessToken: String, refreshToken: String) {
        context.tokenDataStore.edit {
            it[ACCESS_TOKEN] = accessToken
            it[REFRESH_TOKEN] = refreshToken
        }
    }

    val accessToken: Flow<String> = context.tokenDataStore.data.map {
        it[ACCESS_TOKEN] ?: ""
    }

    val refreshToken: Flow<String> = context.tokenDataStore.data.map {
        it[REFRESH_TOKEN] ?: ""
    }
}