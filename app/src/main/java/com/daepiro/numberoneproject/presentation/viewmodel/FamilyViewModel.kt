package com.daepiro.numberoneproject.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.daepiro.numberoneproject.domain.usecase.AroundShelterUseCase
import com.daepiro.numberoneproject.domain.usecase.GetShelterUseCase
import com.daepiro.numberoneproject.presentation.util.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import java.io.File
import javax.inject.Inject

@HiltViewModel
class FamilyViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    private val aroundShelterUseCase: AroundShelterUseCase,
): ViewModel() {
    val isFamilyListManageMode = MutableStateFlow(false)
}