package com.daepiro.numberoneproject.domain.usecase

import com.daepiro.numberoneproject.data.model.CommunityTownReplyRequestBody
import com.daepiro.numberoneproject.data.model.CommunityTownReplyResponseModel
import com.daepiro.numberoneproject.data.network.ApiResult
import com.daepiro.numberoneproject.domain.repository.CommunityRepository
import javax.inject.Inject

class SetCommunityTownReplyWritingUseCase @Inject constructor(private val communityRepository: CommunityRepository) {
    suspend operator fun invoke(token:String,articleId:Int, body: CommunityTownReplyRequestBody):ApiResult<CommunityTownReplyResponseModel>{
        return communityRepository.setTownReply(token,articleId,body)
    }
}