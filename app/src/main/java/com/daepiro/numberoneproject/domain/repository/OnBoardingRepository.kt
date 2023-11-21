package com.daepiro.numberoneproject.domain.repository

import com.daepiro.numberoneproject.data.model.InitDataOnBoardingRequest
import com.daepiro.numberoneproject.data.network.ApiResult

interface OnBoardingRepository {
    suspend fun postinitData(token:String, body: InitDataOnBoardingRequest):ApiResult<Any>
}