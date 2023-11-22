package com.daepiro.numberoneproject.domain.usecase

import com.daepiro.numberoneproject.data.model.CommunityHomeDisasterResponse
import com.daepiro.numberoneproject.data.model.CommunityHomeSituationModel
import com.daepiro.numberoneproject.data.network.ApiResult
import com.daepiro.numberoneproject.domain.repository.CommunityRepository
import javax.inject.Inject

class GetDisasterHomeUseCase @Inject constructor(private val communityRepository: CommunityRepository) {
    suspend operator fun invoke(token:String): ApiResult<CommunityHomeDisasterResponse> {
        return communityRepository.getDisasterHome(token)
    }
}