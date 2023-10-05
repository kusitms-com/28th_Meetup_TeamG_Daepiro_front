package com.example.numberoneproject.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.numberoneproject.R
import com.example.numberoneproject.presentation.util.Extensions.repeatOnStarted
import com.example.numberoneproject.presentation.viewmodel.SampleViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    val viewmodel by viewModels<SampleViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewmodel.getSample()

        repeatOnStarted {
            viewmodel.sampleResponse.collect {
                Log.d("taag", it.toString())
            }
        }
    }
}