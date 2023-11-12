package com.daepiro.numberoneproject.domain.usecase

import android.util.Log
import com.daepiro.numberoneproject.data.model.ShelterData
import com.daepiro.numberoneproject.data.network.ApiResult
import com.daepiro.numberoneproject.data.network.onSuccess
import com.daepiro.numberoneproject.domain.repository.GetShelterRepository
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import java.io.File
import java.io.IOException
import javax.inject.Inject

class GetShelterUseCase @Inject constructor(
    private val getShelterRepository: GetShelterRepository
) {
    suspend operator fun invoke(token:String, file: File): ApiResult<List<ShelterData>>{
        return try{
            val response = getShelterRepository.getShelterDaraLocal(token)
            response.onSuccess { data->
                try{
                    val json = Gson().toJson(data)
                    file.writeText(json)
                    Log.d("GetShelterUseCase", "저장성공")
                }catch (ioException : IOException){
                    Log.e("GetShelterUseCase", "파일쓰기 실패 $ioException")
                    ApiResult.Failure.NetworkError(ioException)
                }
                catch (jsonSyntaxException: JsonSyntaxException){
                    Log.e("GetShelterUseCase", "json 파싱 실패 $jsonSyntaxException")
                    ApiResult.Failure.UnknownApiError(jsonSyntaxException)
                }
            }
            response
        }catch (e:Exception){
            Log.e("GetShelterUseCase", "예외발생 $e")
            ApiResult.Failure.UnknownApiError(e)
        }
    }
}