package com.example.numberoneproject.data.repositoryimpl

import androidx.datastore.core.DataStore
import com.example.numberoneproject.data.model.CheckRequest
import com.example.numberoneproject.data.network.ApiService
import com.example.numberoneproject.domain.repository.CheckTokenRepository
import retrofit2.HttpException
import java.lang.Exception
import java.util.prefs.Preferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CheckTokenRepositoryImpl @Inject constructor(
    private val service: ApiService,
    private val tokenRepositoryImpl: TokenRepositoryImpl
) : CheckTokenRepository {

    override suspend fun checkTokenValidity(token: String): CheckRequest {
        val response = try {
            service.checkToken("Bearer $token")
        } catch (e: HttpException) {
            if (e.code() == 403) {
                // Assuming the issued token is invalid
                service.checkToken("Bearer ${tokenRepositoryImpl.getRefreshToken()}")
            } else {
                throw e
            }
        }

        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                return body
            } else {
                throw Exception("Response body is null")
            }
        } else {
            throw HttpException(response)
        }
    }
}
