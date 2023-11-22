package com.daepiro.numberoneproject.domain.usecase

import com.daepiro.numberoneproject.data.model.CommunityTownReplyDeleteResponse
import com.daepiro.numberoneproject.data.network.ApiResult
import com.daepiro.numberoneproject.domain.repository.CommunityRepository
import javax.inject.Inject

class DeleteCommunityReplyUseCase @Inject constructor(private val communityRepository: CommunityRepository) {
    suspend operator fun invoke(token:String, commentid:Int):ApiResult<CommunityTownReplyDeleteResponse>{
        return communityRepository.deleteReply(token,commentid)
    }
}