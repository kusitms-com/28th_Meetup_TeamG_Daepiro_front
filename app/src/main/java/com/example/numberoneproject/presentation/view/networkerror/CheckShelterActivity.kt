package com.example.numberoneproject.presentation.view.networkerror

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.numberoneproject.R
import com.example.numberoneproject.databinding.ActivityCheckShelterBinding
import com.example.numberoneproject.presentation.base.BaseActivity
import com.example.numberoneproject.presentation.viewmodel.CheckShelterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CheckShelterActivity : BaseActivity<ActivityCheckShelterBinding>(R.layout.activity_check_shelter) {
    val viewModel by viewModels<CheckShelterViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel

        binding.touchContainer.setOnClickListener{
            setLocationSelect()
        }
    }
    fun setLocationSelect(){
        val dialog = LocationSettingDialogFragment()
        dialog.show(supportFragmentManager, "LocationSelect")
    }


}