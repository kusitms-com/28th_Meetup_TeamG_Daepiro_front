package com.daepiro.numberoneproject.domain.usecase

import com.daepiro.numberoneproject.data.model.CommunityTownListModel
import com.daepiro.numberoneproject.data.network.ApiResult
import com.daepiro.numberoneproject.domain.repository.CommunityRepository
import javax.inject.Inject

class CommunityTownListUseCase @Inject constructor(private val communityRepository: CommunityRepository) {
    suspend operator fun invoke(token:String,tag:String?,lastArticleId:Int?,size:Int):ApiResult<CommunityTownListModel>{
        return communityRepository.getTownCommentList(token,size,tag,lastArticleId)
    }
}