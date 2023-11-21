package com.daepiro.numberoneproject.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daepiro.numberoneproject.data.model.DisasterRequestBody
import com.daepiro.numberoneproject.data.model.FamilyListResponse
import com.daepiro.numberoneproject.data.network.onFailure
import com.daepiro.numberoneproject.data.network.onSuccess
import com.daepiro.numberoneproject.domain.usecase.DeleteFamilyUseCase
import com.daepiro.numberoneproject.domain.usecase.GetFamilyListUseCase
import com.daepiro.numberoneproject.domain.usecase.PostFamilySafetyUseCase
import com.daepiro.numberoneproject.presentation.util.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FamilyViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    private val getFamilyListUseCase: GetFamilyListUseCase,
    private val postFamilySafetyUseCase: PostFamilySafetyUseCase,
    private val deleteFamilyUseCase: DeleteFamilyUseCase
): ViewModel() {
    private val _familyList = MutableStateFlow<List<FamilyListResponse>>(emptyList())
    val familyList = _familyList.asStateFlow()

    val isFamilyListManageMode = MutableStateFlow(false)

    val isCompletePostSafety = MutableStateFlow(false)
    val isCompleteDeleteFamily = MutableStateFlow(false)

    fun getFamilyList() {
        viewModelScope.launch {
            val token = "Bearer ${tokenManager.accessToken.first()}"

            getFamilyListUseCase(token)
                .onSuccess {
                    _familyList.value = it
                }
                .onFailure {

                }
        }
    }

    fun postFamilySafety(friendId: Int) {
        isCompletePostSafety.value = false

        viewModelScope.launch {
            val token = "Bearer ${tokenManager.accessToken.first()}"

            postFamilySafetyUseCase(token, friendId)
                .onSuccess {
                    isCompletePostSafety.value = true
                }
                .onFailure {
                    Log.d("taag", it.toString())
                }
        }
    }

    fun deleteFamily(friendId: Int) {
        viewModelScope.launch {
            val token = "Bearer ${tokenManager.accessToken.first()}"

            deleteFamilyUseCase(token, friendId)
                .onSuccess {
                    isCompleteDeleteFamily.value = true
                }
                .onFailure {
                    isCompleteDeleteFamily.value = false
                }
        }
    }
}