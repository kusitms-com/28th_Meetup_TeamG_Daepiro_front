package com.daepiro.numberoneproject.domain.usecase

import com.daepiro.numberoneproject.data.model.ConversationRequestBody
import com.daepiro.numberoneproject.data.network.ApiResult
import com.daepiro.numberoneproject.domain.repository.CommunityRepository
import javax.inject.Inject

class PostDisasterConversationUseCase @Inject constructor(private var communityRepository: CommunityRepository){
    suspend operator fun invoke(token:String, body: ConversationRequestBody): ApiResult<Unit> {
        return communityRepository.postDisasterConversation(token,body)
    }
}