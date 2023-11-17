package com.daepiro.numberoneproject.domain.repository

import com.daepiro.numberoneproject.data.model.CommentWritingRequestBody
import com.daepiro.numberoneproject.data.model.CommentWritingResponse
import com.daepiro.numberoneproject.data.model.CommunityTownDetailData
import com.daepiro.numberoneproject.data.model.CommunityTownListModel
import com.daepiro.numberoneproject.data.model.CommunityTownReplyResponse
import com.daepiro.numberoneproject.data.network.ApiResult
import okhttp3.MultipartBody

interface CommunityRepository {
    suspend fun getTownCommentList(token:String,size:Int,tag:String?,lastArticleId:Int?):ApiResult<CommunityTownListModel>
    suspend fun getTownCommentDetail(token:String,articleId:Int):ApiResult<CommunityTownDetailData>
    suspend fun setTownDetail(
        token:String,
        title:String,
        content:String,
        articleTag:String,
        longtitude:Double,
        latitude:Double,
        imageList: List<MultipartBody.Part>
        ):ApiResult<CommentWritingResponse>

    suspend fun getTownReply(token:String, articleId:Int) : ApiResult<CommunityTownReplyResponse>
}