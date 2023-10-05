package com.example.numberoneproject.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.numberoneproject.R
import com.example.numberoneproject.databinding.ActivityMainBinding
import com.example.numberoneproject.presentation.base.BaseActivity
import com.example.numberoneproject.presentation.util.Extensions.repeatOnStarted
import com.example.numberoneproject.presentation.viewmodel.SampleViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    val viewmodel by viewModels<SampleViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding.viewmodel = viewmodel

        viewmodel.getSample()

    }

    override fun initView() {

    }

    override fun subscribeUi() {
        repeatOnStarted {
            viewmodel.sampleResponse.collect {
                Log.d("taag", it.toString())
            }
        }

    }
}