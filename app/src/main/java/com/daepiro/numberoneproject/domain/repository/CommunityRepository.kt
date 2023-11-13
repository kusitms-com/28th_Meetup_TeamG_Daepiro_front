package com.daepiro.numberoneproject.domain.repository

import com.daepiro.numberoneproject.data.model.CommunityArticleModel
import com.daepiro.numberoneproject.data.network.ApiResult

interface CommunityRepository {
    suspend fun getTownInfoList(token:String,tag:String?,lastArticleId:Int?,page:Int,size:Int,sort:String?):ApiResult<CommunityArticleModel>
}