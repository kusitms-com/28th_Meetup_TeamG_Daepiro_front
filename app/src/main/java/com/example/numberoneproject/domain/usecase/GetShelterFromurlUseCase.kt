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
    suspend operator fun invoke(url:String, file: File): ApiResult<List<ShelterData>>{
        return getShelterRepository.fetchShelterFromUrl(url).onSuccess { data->
            try {
                //val typeOfShelterDataList = object : TypeToken<List<ShelterData>>() {}.type
                //val shelterDataList: List<ShelterData> = Gson().fromJson(data, typeOfShelterDataList)
                val json = Gson().toJson(data)
                file.writeText(json)
                Log.d("getshelterFromusecase", "저장성공")
            }catch (ioException : IOException){
                Log.d("getshelterFromusecase", "실패 $ioException")
                return ApiResult.Failure.NetworkError(ioException)
            }
            catch (jsonSyntaxException:JsonSyntaxException){
                Log.e("GetShelterFromUrlUseCase", "JSON parsing failed $jsonSyntaxException")
            }
        }
    }

}