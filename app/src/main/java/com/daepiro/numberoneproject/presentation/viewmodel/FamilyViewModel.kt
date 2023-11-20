package com.daepiro.numberoneproject.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daepiro.numberoneproject.data.model.DisasterRequestBody
import com.daepiro.numberoneproject.data.model.FamilyListResponse
import com.daepiro.numberoneproject.data.network.onFailure
import com.daepiro.numberoneproject.data.network.onSuccess
import com.daepiro.numberoneproject.domain.usecase.GetFamilyListUseCase
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
    private val getFamilyListUseCase: GetFamilyListUseCase
): ViewModel() {
    private val _familyList = MutableStateFlow<List<FamilyListResponse>>(emptyList())
    val familyList = _familyList.asStateFlow()

    val isFamilyListManageMode = MutableStateFlow(false)

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
}