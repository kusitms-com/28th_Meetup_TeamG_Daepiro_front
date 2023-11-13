package com.daepiro.numberoneproject.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daepiro.numberoneproject.data.network.onFailure
import com.daepiro.numberoneproject.data.network.onSuccess
import com.daepiro.numberoneproject.domain.usecase.GetCommunityTownListUseCase
import com.daepiro.numberoneproject.presentation.util.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CommunityForTownViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    private val getCommunityTownListUseCase: GetCommunityTownListUseCase
) : ViewModel() {
    fun getCommunityTownList(){
        viewModelScope.launch {
            val token = "Bearer ${tokenManager.accessToken.first()}"
            getCommunityTownListUseCase(token,null,null,0,1,null)
                .onSuccess {
                    Log.d("CommunityForTownViewModel","성공")
                }
                .onFailure {
                    Log.e("CommunityForTownViewModel","$it")
                }
        }
    }
}
