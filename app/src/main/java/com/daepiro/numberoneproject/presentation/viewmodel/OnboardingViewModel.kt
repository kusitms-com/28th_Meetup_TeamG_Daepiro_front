package com.daepiro.numberoneproject.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class OnboardingViewModel : ViewModel() {
    private val _showSelectAddress = MutableLiveData<MutableList<String>>(mutableListOf())
    val showSelectAddress:LiveData<MutableList<String>> = _showSelectAddress

    //api요청에 들어갈 데이터항목
    private val _getAddressForApi = MutableLiveData<MutableList<Map<String,String>>>(mutableListOf())
    val getAddressForApi:LiveData<MutableList<Map<String,String>>> = _getAddressForApi


    fun updateShowAddress(address:String){
        val currentList = _showSelectAddress.value ?: mutableListOf()
        currentList.add(address)
        _showSelectAddress.value = currentList
    }

    fun updateData(objectData : Map<String,String>){
        val currentList = _getAddressForApi.value ?: mutableListOf()
        currentList.add(objectData)
        _getAddressForApi.value = currentList
    }

}