package com.daepiro.numberoneproject.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.daepiro.numberoneproject.data.model.AddresseModel
import com.daepiro.numberoneproject.data.model.DisasterTypeModel
import com.daepiro.numberoneproject.data.model.DisastertypeDataModel
import com.daepiro.numberoneproject.data.model.InitDataOnBoardingRequest
import com.daepiro.numberoneproject.data.network.onFailure
import com.daepiro.numberoneproject.data.network.onSuccess
import com.daepiro.numberoneproject.domain.repository.OnBoardingRepository
import com.daepiro.numberoneproject.domain.usecase.OnBoardingUseCase
import com.daepiro.numberoneproject.presentation.util.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    private val onBoardingUseCase: OnBoardingUseCase
) : ViewModel() {
    private val _showSelectAddress = MutableLiveData<MutableList<String>>(mutableListOf())
    val showSelectAddress:LiveData<MutableList<String>> = _showSelectAddress

    //api요청에 들어갈 데이터항목
    var getAddressForApi = mutableListOf<AddresseModel>()

    var realname:String = ""
    var nickname:String =""
    var fcmToken:String =""
    var disasterType = listOf<DisasterTypeModel>()

    fun updateShowAddress(address:String){
        val currentList = _showSelectAddress.value ?: mutableListOf()
        currentList.add(address)
        _showSelectAddress.value = currentList
    }

    fun updateData(objectData: Map<String, String>) {
        val addressModel = AddresseModel(
            lv1 = objectData["lv1"] ?: "",
            lv2 = objectData["lv2"] ?: "",
            lv3 = objectData["lv3"] ?: ""
        )

        // getAddressForApi 리스트에 생성된 AddresseModel 객체 추가
        getAddressForApi.add(addressModel)
    }

    //온보딩시 입력한 데이터 전송할 api호출 함수
    suspend fun postInitData(body: InitDataOnBoardingRequest){
        val token = "Bearer ${tokenManager.accessToken.first()}"
        onBoardingUseCase.invoke(token,body)
            .onSuccess {
                Log.d("postInitData" , "$it")
            }
            .onFailure {
                Log.e("postInitData" , "$it")
            }
    }

}