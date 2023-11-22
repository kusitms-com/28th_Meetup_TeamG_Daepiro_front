package com.daepiro.numberoneproject.domain.usecase

import com.daepiro.numberoneproject.data.model.CommunityTownListModel
import com.daepiro.numberoneproject.data.network.ApiResult
import com.daepiro.numberoneproject.domain.repository.CommunityRepository
import javax.inject.Inject

class GetCommunityTownListUseCase @Inject constructor(private val communityRepository: CommunityRepository) {
    suspend operator fun invoke(token:String,size:Int,tag:String?,lastArticleId:Int?,longtitude: Double?, latitude: Double?,regionLv2:String):ApiResult<CommunityTownListModel>{
        return communityRepository.getTownCommentList(token,size,tag,lastArticleId,longtitude,latitude,regionLv2)
    }
}