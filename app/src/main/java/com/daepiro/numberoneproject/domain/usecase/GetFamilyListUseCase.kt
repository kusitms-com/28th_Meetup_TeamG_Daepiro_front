package com.daepiro.numberoneproject.domain.usecase

import com.daepiro.numberoneproject.data.model.FamilyListResponse
import com.daepiro.numberoneproject.data.model.ShelterListResponse
import com.daepiro.numberoneproject.data.model.ShelterRequestBody
import com.daepiro.numberoneproject.data.network.ApiResult
import com.daepiro.numberoneproject.domain.repository.FamilyRepository
import com.daepiro.numberoneproject.domain.repository.ShelterRepository
import javax.inject.Inject

class GetFamilyListUseCase @Inject constructor(
    private val familyRepository: FamilyRepository
) {
    suspend operator fun invoke(
        token: String
    ): ApiResult<List<FamilyListResponse>> {
        return familyRepository.getFamilyList(token)
    }
}