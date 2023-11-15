package com.daepiro.numberoneproject.presentation.util

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.daepiro.numberoneproject.data.network.onFailure
import com.daepiro.numberoneproject.data.network.onSuccess
import com.daepiro.numberoneproject.domain.usecase.GetUserHeartUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.first

@HiltWorker
class SomeWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val tokenManager: TokenManager,
    private val usecase: GetUserHeartUseCase
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result = coroutineScope {
        val token = "Bearer ${tokenManager.accessToken.first()}"

        try {
            usecase(token)
                .onSuccess {
                    Log.d("taag", it.toString())
                }
                .onFailure {
                    Log.d("taag", "usecase 실패")
                }
            Result.success()
        } catch (e: Exception) {
            Log.e("taag","Worker Exception $e")
            Result.failure()
        }
    }


}
