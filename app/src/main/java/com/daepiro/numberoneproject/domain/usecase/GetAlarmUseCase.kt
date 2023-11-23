package com.daepiro.numberoneproject.domain.usecase

import com.daepiro.numberoneproject.data.model.AlarmResponse
import com.daepiro.numberoneproject.data.model.DisasterResponse
import com.daepiro.numberoneproject.data.model.FamilyListResponse
import com.daepiro.numberoneproject.data.model.UserHeartResponse
import com.daepiro.numberoneproject.data.network.ApiResult
import com.daepiro.numberoneproject.domain.repository.AlarmRepository
import com.daepiro.numberoneproject.domain.repository.FamilyRepository
import com.daepiro.numberoneproject.domain.repository.FundingRepository
import javax.inject.Inject

class GetAlarmUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository
) {
    suspend operator fun invoke(
        token: String,
        page: Int,
        size: Int,
        isDisaster: Boolean
    ): ApiResult<AlarmResponse> {
        return alarmRepository.getAlarmList(token, page, size, isDisaster)
    }
}