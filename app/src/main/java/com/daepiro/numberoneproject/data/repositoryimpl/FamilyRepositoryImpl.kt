package com.daepiro.numberoneproject.data.repositoryimpl

import com.daepiro.numberoneproject.data.model.DisasterRequestBody
import com.daepiro.numberoneproject.data.model.DisasterResponse
import com.daepiro.numberoneproject.data.model.FamilyListResponse
import com.daepiro.numberoneproject.data.network.ApiResult
import com.daepiro.numberoneproject.data.network.ApiService
import com.daepiro.numberoneproject.domain.repository.DisasterRepository
import com.daepiro.numberoneproject.domain.repository.FamilyRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FamilyRepositoryImpl @Inject constructor(
    private val service: ApiService
): FamilyRepository {
    override suspend fun getFamilyList(token: String): ApiResult<List<FamilyListResponse>> {
        return service.getFamilyList(token)
    }

    override suspend fun postFamilySafety(token: String, friendId: Int): ApiResult<Any> {
        return service.postFamilySafety(token, friendId)
    }
}