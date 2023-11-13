package com.daepiro.numberoneproject.data.repositoryimpl

import com.daepiro.numberoneproject.data.model.CommunityArticleModel
import com.daepiro.numberoneproject.data.network.ApiResult
import com.daepiro.numberoneproject.data.network.ApiService
import com.daepiro.numberoneproject.domain.repository.CommunityRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommunityRepositoryImpl @Inject constructor(
    private val service: ApiService
):CommunityRepository {
    override suspend fun getTownInfoList(
        token:String,
        tag:String?,
        lastArticleId:Int?,
        page:Int,
        size:Int,
        sort:String?
    ):ApiResult<CommunityArticleModel>{
        return service.getTownInfoArticle(token,tag,lastArticleId,page,size,sort)
    }
}