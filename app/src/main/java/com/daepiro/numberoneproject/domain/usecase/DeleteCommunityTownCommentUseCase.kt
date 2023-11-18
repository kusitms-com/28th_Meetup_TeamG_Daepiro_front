package com.daepiro.numberoneproject.domain.usecase

import com.daepiro.numberoneproject.data.model.CommunityTownDeleteCommentResponse
import com.daepiro.numberoneproject.data.network.ApiResult
import com.daepiro.numberoneproject.domain.repository.CommunityRepository
import javax.inject.Inject

class DeleteCommunityTownCommentUseCase @Inject constructor(private val communityRepository: CommunityRepository) {
    suspend operator fun invoke(token:String, articleid:Int):ApiResult<CommunityTownDeleteCommentResponse>{
        return communityRepository.deleteComment(token,articleid)
    }
}