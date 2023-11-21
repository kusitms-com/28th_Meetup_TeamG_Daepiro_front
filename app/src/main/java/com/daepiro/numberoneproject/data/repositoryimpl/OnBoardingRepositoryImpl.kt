package com.daepiro.numberoneproject.data.repositoryimpl

import com.daepiro.numberoneproject.data.model.InitDataOnBoardingRequest
import com.daepiro.numberoneproject.data.network.ApiResult
import com.daepiro.numberoneproject.data.network.ApiService
import com.daepiro.numberoneproject.domain.repository.OnBoardingRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OnBoardingRepositoryImpl @Inject constructor(
    private val service: ApiService
): OnBoardingRepository {
    override suspend fun postinitData(token:String, body: InitDataOnBoardingRequest) :ApiResult<Any>{
        return service.postinitData(token,body)
    }
}