package com.daepiro.numberoneproject.domain.repository

import com.daepiro.numberoneproject.data.model.DisasterRequestBody
import com.daepiro.numberoneproject.data.model.DisasterResponse
import com.daepiro.numberoneproject.data.model.FamilyListResponse
import com.daepiro.numberoneproject.data.network.ApiResult

interface FamilyRepository {
    suspend fun getFamilyList(token: String): ApiResult<List<FamilyListResponse>>
}