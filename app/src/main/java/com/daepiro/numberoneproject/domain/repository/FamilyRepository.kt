package com.daepiro.numberoneproject.domain.repository

import com.daepiro.numberoneproject.data.model.DisasterRequestBody
import com.daepiro.numberoneproject.data.model.DisasterResponse
import com.daepiro.numberoneproject.data.model.FamilyListResponse
import com.daepiro.numberoneproject.data.model.SendSafetyResponse
import com.daepiro.numberoneproject.data.network.ApiResult

interface FamilyRepository {
    suspend fun getFamilyList(token: String): ApiResult<List<FamilyListResponse>>
    suspend fun postFamilySafety(token: String, friendId: Int): ApiResult<SendSafetyResponse>
    suspend fun deleteFamily(token: String, friendId: Int): ApiResult<FamilyListResponse>
}