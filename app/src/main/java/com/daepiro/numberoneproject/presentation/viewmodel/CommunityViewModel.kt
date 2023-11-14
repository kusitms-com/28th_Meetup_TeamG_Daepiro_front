package com.daepiro.numberoneproject.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daepiro.numberoneproject.data.model.CommunityTownListModel
import com.daepiro.numberoneproject.data.network.onFailure
import com.daepiro.numberoneproject.data.network.onSuccess
import com.daepiro.numberoneproject.domain.usecase.CommunityTownListUseCase
import com.daepiro.numberoneproject.presentation.util.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    private val communityTownListUseCase: CommunityTownListUseCase
) : ViewModel() {

    private val _data = MutableLiveData<CommunityTownListModel>()
    val data: LiveData<CommunityTownListModel> = _data
    fun getTownCommentList(){
        viewModelScope.launch {
            val token = "Bearer ${tokenManager.accessToken.first()}"
            communityTownListUseCase(token,null,null,5)
                .onSuccess {datalist->
                    _data.postValue(datalist)
                    Log.d("CommunityForTownViewModel","성공")
//                    Log.d("CommunityForTownViewModelsuccess","${_data.value}")
                }
                .onFailure {
                    Log.e("CommunityForTownViewModel","$it")
                }
        }
    }
}