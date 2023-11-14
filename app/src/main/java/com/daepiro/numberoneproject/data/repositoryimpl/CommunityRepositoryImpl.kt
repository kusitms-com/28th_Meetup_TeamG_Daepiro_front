package com.daepiro.numberoneproject.data.repositoryimpl

import com.daepiro.numberoneproject.data.model.CommunityTownListModel
import com.daepiro.numberoneproject.data.network.ApiResult
import com.daepiro.numberoneproject.data.network.ApiService
import com.daepiro.numberoneproject.domain.repository.CommunityRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommunityRepositoryImpl @Inject constructor(
    private val service:ApiService
):CommunityRepository {
    override suspend fun getTownCommentList(
        token:String,
        size:Int,
        tag:String?,
        lastArticleId:Int?
    ):ApiResult<CommunityTownListModel>{
        return service.getTownCommentList(token,size,tag,lastArticleId)
    }
}