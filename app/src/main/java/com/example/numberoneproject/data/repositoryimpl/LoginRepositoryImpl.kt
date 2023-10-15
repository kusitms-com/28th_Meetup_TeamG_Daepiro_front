package com.example.numberoneproject.data.repositoryimpl

import com.example.numberoneproject.data.model.LoginBody
import com.example.numberoneproject.data.model.LoginResponse
import com.example.numberoneproject.data.network.ApiResult
import com.example.numberoneproject.data.network.ApiService
import com.example.numberoneproject.domain.repository.LoginRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepositoryImpl @Inject constructor(
    private val service: ApiService
): LoginRepository {
    override suspend fun userLogin(loginBody: LoginBody): ApiResult<LoginResponse> {
        return service.userLogin(loginBody)
    }
}