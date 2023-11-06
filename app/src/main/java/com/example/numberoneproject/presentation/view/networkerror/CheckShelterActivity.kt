package com.example.numberoneproject.presentation.view.networkerror

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.numberoneproject.R
import com.example.numberoneproject.databinding.ActivityCheckShelterBinding
import com.example.numberoneproject.presentation.base.BaseActivity
import com.example.numberoneproject.presentation.viewmodel.CheckShelterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CheckShelterActivity : BaseActivity<ActivityCheckShelterBinding>(R.layout.activity_check_shelter) {
    val viewModel by viewModels<CheckShelterViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel

        binding.touchContainer.setOnClickListener{
            setLocationSelect()
        }
        check(this)

    }
    fun setLocationSelect(){
        val dialog = LocationSettingDialogFragment()
        dialog.show(supportFragmentManager, "LocationSelect")
    }
    fun check(context: Context){
        val (cities, districts, dongs) = viewModel.readFileGetData(context) ?: Triple(emptyList(),emptyList(),emptyList())
        val uniqueCities = cities.distinct()
        Log.d("loc","$uniqueCities")
        Log.d("loc","$districts")
        Log.d("loc","$dongs")
    }


}