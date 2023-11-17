package com.daepiro.numberoneproject.presentation.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daepiro.numberoneproject.data.model.CommentWritingRequestBody
import com.daepiro.numberoneproject.data.model.CommentWritingResponse
import com.daepiro.numberoneproject.data.model.CommunityTownDetailData
import com.daepiro.numberoneproject.data.model.CommunityTownListModel
import com.daepiro.numberoneproject.data.model.CommunityTownReplyResponse
import com.daepiro.numberoneproject.data.network.onFailure
import com.daepiro.numberoneproject.data.network.onSuccess
import com.daepiro.numberoneproject.domain.usecase.GetCommunityTownDetailUseCase
import com.daepiro.numberoneproject.domain.usecase.GetCommunityTownListUseCase
import com.daepiro.numberoneproject.domain.usecase.GetTownReplyUseCase
import com.daepiro.numberoneproject.domain.usecase.SetCommunityWritingUseCase

import com.daepiro.numberoneproject.presentation.util.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    private val getCommunityTownListUseCase: GetCommunityTownListUseCase,
    private val getCommunityTownDetailUseCase: GetCommunityTownDetailUseCase,
    private val setCommunityWritingUseCase: SetCommunityWritingUseCase,
    private val getTownReplyUseCase: GetTownReplyUseCase
) : ViewModel() {

    private val _townCommentList = MutableStateFlow(CommunityTownListModel())
    val townCommentList=_townCommentList.asStateFlow()

    private val _townDetail = MutableStateFlow(CommunityTownDetailData())
    val townDetail=_townDetail.asStateFlow()

    val _isVisible = MutableLiveData<Boolean>()
    val isVisible:LiveData<Boolean> = _isVisible

    private val _writingResult = MutableStateFlow(CommentWritingResponse())
    val writingResult = _writingResult.asStateFlow()

    private val _replyResult = MutableStateFlow(CommunityTownReplyResponse())
    val replyResult = _replyResult.asStateFlow()

    val _tagData = MutableLiveData<String>()
    val tagData:LiveData<String> = _tagData


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
                }
                .onFailure {
                    Log.e("getTownDetail","$it")
                }
        }
    }

    fun postComment(title:String,content:String,articleTag:String,longtitude:Double,latitude:Double, imageList:List<MultipartBody.Part>){
        viewModelScope.launch {
            val token = "Bearer ${tokenManager.accessToken.first()}"
            setCommunityWritingUseCase.invoke(token,title,content,articleTag,longtitude,latitude,imageList)
                .onSuccess {
                    _writingResult.value = it
                    Log.d("CommunityViewModel", "성공성공성공")
                    Log.d("CommunityViewModel", "$it")
                }
                .onFailure {
                    Log.d("CommunityViewModel1", "$it")
                }

        }
    }

    fun getReply(articleId:Int){
        viewModelScope.launch {
            val token = "Bearer ${tokenManager.accessToken.first()}"
            getTownReplyUseCase.invoke(token,articleId)
                .onSuccess {response->
                    _replyResult.value = response
                    Log.d("getReply", "$response")
                }
                .onFailure {
                    Log.e("getReply","$it")
                }
        }
    }



    val tagText: StateFlow<String> = townDetail
        .map { detail -> tagTextForDetail(detail.articleTag) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = ""
        )
    @RequiresApi(Build.VERSION_CODES.O)
    val detailTime:StateFlow<String> = townDetail
        .map { detail -> getTimeDifference(detail.createdAt) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = ""
        )


    private fun tagTextForDetail(articleTag:String):String{
        return when(articleTag){
            "SAFETY" -> "안전"
            "LIFE" -> "일상"
            else -> "교통"
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getTimeDifference(createdTime: String): String {
        if (createdTime.isBlank()) {
            return "기본값 또는 오류 메시지"
        }

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
        try {
            val createdDateTime = LocalDateTime.parse(createdTime, formatter)
            val currentDateTime = LocalDateTime.now()
            val duration = Duration.between(createdDateTime, currentDateTime)

            return when {
                duration.toHours() < 1 -> "${duration.toMinutes()}분 전"
                duration.toDays() < 1 -> String.format("%02d:%02d",createdDateTime.hour,createdDateTime.minute)
                else -> "${createdDateTime.monthValue}/${createdDateTime.dayOfMonth}"
            }
        } catch (e: DateTimeParseException) {
            Log.e("getTimeDifference", "$e")
            return "파싱 오류"
        }
    }
}

