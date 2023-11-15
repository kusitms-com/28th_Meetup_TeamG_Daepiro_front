package com.daepiro.numberoneproject.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daepiro.numberoneproject.data.model.CommunityTownDetailData
import com.daepiro.numberoneproject.data.model.CommunityTownListModel
import com.daepiro.numberoneproject.data.network.onFailure
import com.daepiro.numberoneproject.data.network.onSuccess
import com.daepiro.numberoneproject.domain.usecase.GetCommunityTownDetailUseCase
import com.daepiro.numberoneproject.domain.usecase.GetCommunityTownListUseCase
import com.daepiro.numberoneproject.presentation.util.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    private val getCommunityTownListUseCase: GetCommunityTownListUseCase,
    private val getCommunityTownDetailUseCase: GetCommunityTownDetailUseCase
) : ViewModel() {

//    private val _data = MutableLiveData<CommunityTownListModel>()
//    val data: LiveData<CommunityTownListModel> = _data

    private val _townCommentList = MutableStateFlow(CommunityTownListModel())
    val townCommentList=_townCommentList.asStateFlow()

    private val _townDetail = MutableStateFlow(CommunityTownDetailData())
    val townDetail=_townDetail.asStateFlow()

    fun getTownCommentList(size:Int,tag:String?,lastArticleId:Int?){
        viewModelScope.launch {
            val token = "Bearer ${tokenManager.accessToken.first()}"
            getCommunityTownListUseCase(token,tag,lastArticleId,size)
                .onSuccess {datalist->
                    _townCommentList.value = datalist
                    Log.d("CommunityForTownViewModel","성공")
                }
                .onFailure {
                    Log.e("CommunityForTownViewModel","$it")
                }
        }
    }
    fun getTownDetail(articleId: Int){
        viewModelScope.launch {
            val token = "Bearer ${tokenManager.accessToken.first()}"
            getCommunityTownDetailUseCase(token,articleId)
                .onSuccess {deatilData->
                    _townDetail.value = deatilData
                    Log.d("getTownDetail","성공")
                }
                .onFailure {
                    Log.e("getTownDetail","$it")
                }
        }
    }
}