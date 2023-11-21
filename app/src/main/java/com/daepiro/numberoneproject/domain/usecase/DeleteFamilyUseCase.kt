package com.daepiro.numberoneproject.domain.usecase

import com.daepiro.numberoneproject.data.model.FamilyListResponse
import com.daepiro.numberoneproject.data.model.UserHeartResponse
import com.daepiro.numberoneproject.data.network.ApiResult
import com.daepiro.numberoneproject.domain.repository.FamilyRepository
import com.daepiro.numberoneproject.domain.repository.FundingRepository
import javax.inject.Inject

class DeleteFamilyUseCase @Inject constructor(
    private val familyRepository: FamilyRepository
) {
    suspend operator fun invoke(
        token: String,
        friendId: Int
    ): ApiResult<FamilyListResponse> {
        return familyRepository.deleteFamily(token, friendId)
    }
}