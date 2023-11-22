package com.daepiro.numberoneproject.domain.usecase

import com.daepiro.numberoneproject.data.model.GetRegionResponse
import com.daepiro.numberoneproject.data.network.ApiResult
import com.daepiro.numberoneproject.domain.repository.CommunityRepository
import javax.inject.Inject

class GetTownListUseCase @Inject constructor(private val communityRepository: CommunityRepository) {
    suspend operator fun invoke(token:String) : ApiResult<GetRegionResponse>{
        return communityRepository.getTownList(token)
    }
}