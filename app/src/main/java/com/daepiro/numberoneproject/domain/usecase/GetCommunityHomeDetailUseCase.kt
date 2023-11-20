package com.daepiro.numberoneproject.domain.usecase

import com.daepiro.numberoneproject.data.model.CommunityDisasterDetailResponse
import com.daepiro.numberoneproject.data.network.ApiResult
import com.daepiro.numberoneproject.domain.repository.CommunityRepository
import javax.inject.Inject

class GetCommunityHomeDetailUseCase @Inject constructor(private val communityRepository: CommunityRepository) {
    suspend operator fun invoke(token:String,sort:String,disasterId:Int):ApiResult<CommunityDisasterDetailResponse> {
        return communityRepository.getDisasterHomeDetail(token,sort,disasterId)
    }
}