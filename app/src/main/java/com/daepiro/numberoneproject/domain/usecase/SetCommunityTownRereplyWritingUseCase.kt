package com.daepiro.numberoneproject.domain.usecase

import com.daepiro.numberoneproject.data.model.CommunityRereplyRequestBody
import com.daepiro.numberoneproject.data.model.CommunityTownReplyResponseModel
import com.daepiro.numberoneproject.data.network.ApiResult
import com.daepiro.numberoneproject.domain.repository.CommunityRepository
import javax.inject.Inject

class SetCommunityTownRereplyWritingUseCase @Inject constructor(private val communityRepository: CommunityRepository) {
    suspend operator fun invoke(token:String,articleid:Int,commentid:Int,body: CommunityRereplyRequestBody
    ):ApiResult<CommunityTownReplyResponseModel>{
        return communityRepository.setTownRereply(token,articleid,commentid,body)
    }
}