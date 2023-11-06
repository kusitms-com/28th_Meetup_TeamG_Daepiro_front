package com.example.numberoneproject.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.numberoneproject.data.model.ShelterData
import com.example.numberoneproject.presentation.view.networkerror.LocationSettingDialogFragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

class CheckShelterViewModel : ViewModel() {
    //데이터가 정제되지 않아 추출 할 수 없음...
    fun extractData(file: File): List<ShelterData>?{
        return try{
            val json = file.readText()
            val listType = object : TypeToken<List<ShelterData>>() {}.type
            Gson().fromJson(json, listType)//json 리스트로 변환
        }catch (e:Exception){
            e.printStackTrace()
            null
        }
    }

    fun getNameFromData(shelterDataList: List<ShelterData>): Triple<List<String>, List<String>, List<String>>{
        val cities = shelterDataList.map{it.addressDetail.city}.distinct()
        val districts = shelterDataList.map{it.addressDetail.district}.distinct()
        val dongs = shelterDataList.map { it.addressDetail.dong }.distinct()

        return Triple(cities, districts, dongs)
    }

    fun readFileGetData(context: Context): Triple<List<String>,List<String>,List<String>>{
        val filePath = "/data/data/${context.packageName}/files/shelter_data.json"
        val file= File(filePath)
        val shelterDataList = extractData(file)
        if(shelterDataList == null){
            return Triple(emptyList(),emptyList(),emptyList())
        }
        return getNameFromData(shelterDataList)
    }
}