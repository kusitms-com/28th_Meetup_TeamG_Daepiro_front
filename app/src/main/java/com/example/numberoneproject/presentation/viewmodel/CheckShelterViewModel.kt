package com.example.numberoneproject.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.numberoneproject.data.model.ShelterData
import com.example.numberoneproject.presentation.view.networkerror.LocationSettingDialogFragment
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

class CheckShelterViewModel : ViewModel() {
        val _isactive = MutableLiveData<Boolean>()
        val isactive:LiveData<Boolean> = _isactive

        //주소 담는 flow
        val _selectaddress = MutableLiveData<String>()
        val selectaddress : LiveData<String> = _selectaddress

    //데이터 리스트를 담는 flow
    private val _currentList = MutableStateFlow<List<JSONObject>>(emptyList())
    val currentList : StateFlow<List<JSONObject>> = _currentList

    //ui가시성 처리하기 위함
    val isEmptyList: LiveData<Boolean> = _currentList.map {it.isEmpty()}.asLiveData()
    init {
        _isactive.value = false
    }

    fun extractShelterFromLocal(context:Context, fileName:String, selectAddress:String, shelterType : String):List<JSONObject>{
        val file = File(context.filesDir, fileName)
        val jsonString = file.readText()
        val jsonArray = JSONArray(jsonString)
        val filteredList = mutableListOf<JSONObject>()
        for(i in 0 until jsonArray.length()){
            val jsonObject = jsonArray.getJSONObject(i)
            val currentType = jsonObject.getString("shelterType")
            val matchesType = shelterType.isEmpty() || currentType == shelterType
            if(jsonObject.getString("fullAddress").contains(selectAddress) && matchesType){
                filteredList.add(jsonObject)
            }
        }
        return filteredList
    }

    fun updateShelterList(context:Context, fileName:String,selectAddress:String, shelterType : String){
        viewModelScope.launch {
            val newList = extractShelterFromLocal(context, fileName,selectAddress, shelterType)
            _currentList.value = newList
        }
    }

}