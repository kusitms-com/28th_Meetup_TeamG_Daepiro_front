package com.example.numberoneproject.domain.usecase

import com.example.numberoneproject.data.model.SampleResponse
import com.example.numberoneproject.data.network.ApiResult
import com.example.numberoneproject.domain.repository.SampleRepository
import javax.inject.Inject
//생성자 주입을 사용해 의존성을 받아온다
class SampleUseCase @Inject constructor(
    private val sampleRepository: SampleRepository
) {
    //invoke는 연산자 오버로딩 일부로 클래스 객체를 직접 호출할 수 있게 해준다
    suspend operator fun invoke(): ApiResult<SampleResponse> {
        return sampleRepository.getSampleApi()
    }
}