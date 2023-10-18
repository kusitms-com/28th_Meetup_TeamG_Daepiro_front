package com.example.numberoneproject.data.repositoryimpl

import android.content.Context
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.numberoneproject.data.repositoryimpl.TokenRepositoryImpl.PreferenceKeys.ACCESS_TOKEN
import com.example.numberoneproject.data.repositoryimpl.TokenRepositoryImpl.PreferenceKeys.LOGIN_CHECK
import com.example.numberoneproject.data.repositoryimpl.TokenRepositoryImpl.PreferenceKeys.REFRESH_TOKEN
import com.example.numberoneproject.domain.repository.TokenRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenRepositoryImpl @Inject constructor(@ApplicationContext private val context: Context):TokenRepository {
    private object PreferenceKeys{
        val ACCESS_TOKEN = stringPreferencesKey("access_token")
        val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
        val LOGIN_CHECK = booleanPreferencesKey("login_check")
    }

    private val Context.tokenDataStore by preferencesDataStore("TOKEN_DATASTORE")
    private val Context.loginCheckDataStore by preferencesDataStore("LOGIN_CHECK_DATASTORE")

    override suspend fun saveToken(token:List<String>){
        context.tokenDataStore.edit{prefs ->
            prefs[ACCESS_TOKEN] = token.first()
            prefs[REFRESH_TOKEN] = token.last()
        }
        //token들이 제대로 들어왔는지 여부 확인
        context.loginCheckDataStore.edit{prefs->
            prefs[LOGIN_CHECK] = true
        }
    }

    override suspend fun getToken(): Flow<List<String>>{
        return context.tokenDataStore.data
            .catch{exception->
                if(exception is IOException){
                    exception.printStackTrace()
                    emit(emptyPreferences())
                }else{
                    throw exception
                }
            }
            .map{prefs->
                prefs.asMap().values.toList().map{
                    it.toString()
                }
            }
    }

    override suspend fun getIsLogin(): Flow<Boolean>{
        return context.loginCheckDataStore.data
            .map{prefs->
                prefs[LOGIN_CHECK] ?: false
            }
    }

    suspend fun getRefreshToken():String{
        val refreshToken = context.tokenDataStore.data.map{prefs->
            prefs[PreferenceKeys.REFRESH_TOKEN] ?: ""
        }
        return refreshToken.first()
    }

    override suspend fun setIsLogin(isLogin:Boolean){
        context.loginCheckDataStore.edit { prefs ->
            prefs[LOGIN_CHECK] = isLogin
        }
    }
}