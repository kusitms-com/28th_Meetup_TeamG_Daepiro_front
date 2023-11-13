package com.daepiro.numberoneproject.domain.usecase

import com.daepiro.numberoneproject.data.model.CommunityArticleModel
import com.daepiro.numberoneproject.data.network.ApiResult
import com.daepiro.numberoneproject.domain.repository.CommunityRepository
import javax.inject.Inject

class GetCommunityTownListUseCase @Inject constructor(private val communityRepository: CommunityRepository) {
    suspend operator fun invoke(token:String,tag:String?,lastArticleId:Int?,page:Int,size:Int,sort:String?):ApiResult<CommunityArticleModel>{
        return communityRepository.getTownInfoList(token,tag,lastArticleId,page,size,sort)
    }
}