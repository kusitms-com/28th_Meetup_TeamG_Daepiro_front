package com.daepiro.numberoneproject.domain.repository

import com.daepiro.numberoneproject.data.model.CommunityTownDetailData
import com.daepiro.numberoneproject.data.model.CommunityTownListModel
import com.daepiro.numberoneproject.data.network.ApiResult

interface CommunityRepository {
    suspend fun getTownCommentList(token:String,size:Int,tag:String?,lastArticleId:Int?):ApiResult<CommunityTownListModel>
    suspend fun getTownCommentDetail(token:String,articleId:Int):ApiResult<CommunityTownDetailData>
}