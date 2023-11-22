package com.daepiro.numberoneproject.domain.usecase

import com.daepiro.numberoneproject.data.model.CommunityTownReplyResponse
import com.daepiro.numberoneproject.data.network.ApiResult
import com.daepiro.numberoneproject.domain.repository.CommunityRepository
import javax.inject.Inject

class GetTownReplyUseCase @Inject constructor(private val communityRepository: CommunityRepository) {
    suspend operator fun invoke(token:String, articleId:Int): ApiResult<CommunityTownReplyResponse> {
        return communityRepository.getTownReply(token,articleId)
    }
}