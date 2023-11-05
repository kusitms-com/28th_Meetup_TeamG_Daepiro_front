package com.example.numberoneproject.domain.usecase

import android.util.Log
import com.example.numberoneproject.data.model.ShelterData
import com.example.numberoneproject.data.network.ApiResult
import com.example.numberoneproject.data.network.onSuccess
import com.example.numberoneproject.domain.repository.GetShelterRepository
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.IOException
import javax.inject.Inject

class GetShelterFromurlUseCase @Inject constructor(
    private val getShelterRepository: GetShelterRepository
) {

    suspend operator fun invoke(url: String, file: File): ApiResult<List<ShelterData>> {
        return try {
            val response = getShelterRepository.fetchShelterFromUrl(url)
            response.onSuccess { data ->
                try {
                    val json = Gson().toJson(data)
                    file.writeText(json)
                    Log.d("GetShelterFromUrlUseCase", "저장 성공")
                } catch (ioException: IOException) {
                    Log.d("GetShelterFromUrlUseCase", "파일 쓰기 실패: $ioException")
                    return ApiResult.Failure.NetworkError(ioException)
                } catch (jsonSyntaxException: JsonSyntaxException) {
                    Log.e("GetShelterFromUrlUseCase", "JSON 파싱 실패: $jsonSyntaxException")
                    return ApiResult.Failure.UnknownApiError(jsonSyntaxException)

                }
            }
            response
        } catch (e: Exception) {
            Log.e("GetShelterFromUrlUseCase", "예외 발생: $e")
            ApiResult.Failure.UnknownApiError(e)
        }
    }


}