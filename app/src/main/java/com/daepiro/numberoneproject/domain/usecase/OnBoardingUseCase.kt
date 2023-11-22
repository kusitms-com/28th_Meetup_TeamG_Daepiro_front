package com.daepiro.numberoneproject.domain.usecase

import com.daepiro.numberoneproject.data.model.InitDataOnBoardingRequest
import com.daepiro.numberoneproject.data.network.ApiResult
import com.daepiro.numberoneproject.domain.repository.OnBoardingRepository
import javax.inject.Inject

class OnBoardingUseCase @Inject constructor(private val onBoardingRepository: OnBoardingRepository) {
    suspend operator fun invoke(
        token:String,
        body: InitDataOnBoardingRequest
    ):ApiResult<Any>{
        return onBoardingRepository.postinitData(token,body)
    }
}