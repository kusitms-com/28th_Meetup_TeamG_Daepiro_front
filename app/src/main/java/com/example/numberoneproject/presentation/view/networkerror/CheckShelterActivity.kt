package com.example.numberoneproject.presentation.view.networkerror

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.example.numberoneproject.R
import com.example.numberoneproject.data.model.ShelterData
import com.example.numberoneproject.data.model.ShelterRecyclerList
import com.example.numberoneproject.databinding.ActivityCheckShelterBinding
import com.example.numberoneproject.presentation.base.BaseActivity
import com.example.numberoneproject.presentation.util.Extensions.repeatOnStarted
import com.example.numberoneproject.presentation.viewmodel.CheckShelterViewModel
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File

@AndroidEntryPoint
class CheckShelterActivity : BaseActivity<ActivityCheckShelterBinding>(R.layout.activity_check_shelter) {
    private val locationSettingDialogFragment = LocationSettingDialogFragment()
    val viewModel by viewModels<CheckShelterViewModel>()
    private lateinit var adapter : ShelterListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        adapter = ShelterListAdapter()
        binding.recyclerList.adapter = adapter
        binding.includeAppBar.appBarText.text = "대피소 조회"

        binding.includeAppBar.backBtn.setOnClickListener{
            onBackPressed()
        }

        setupTabLayout()

        viewModel.currentList.asLiveData().observe(this, Observer { list->
            val shelterToList = list.map{jsonObject ->
                ShelterRecyclerList(
                    fullAddress = jsonObject.getString("fullAddress") ,
                    facilityFullName = jsonObject.getString("facilityFullName")
                )
            }
            Log.d("checkshelter", "${viewModel.currentList}")
            adapter.updateShelters(shelterToList)
        })

        viewModel.shelterListUpdate.observe(this, Observer { shouldUpdate ->
            if (shouldUpdate) {
                updateShelterListBasedOnTab(binding.tabLayout.selectedTabPosition)
            }
        })


        binding.touchContainer.setOnClickListener{
            setLocationSelect()
        }


    }

    override fun onStart() {
        super.onStart()
        locationSettingDialogFragment.setOnItemSelectedListener(object : LocationSettingDialogFragment.OnItemSelectedListener{
            override fun onItemSelected(sendItems: String) {
                Log.d("CheckShelterActivity", "$sendItems")
            }

        })
    }
    private var previousIndex = 0
    private fun setupTabLayout(){
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                updateShelterListBasedOnTab(tab?.position)
            }


            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }
    private fun getShelterType(tabPosition:Int?):String{
        return when(tabPosition){
            0 -> ""
            1-> "EARTHQUAKE"
            2->"CIVIL_DEFENCE"
            else-> "FLOOD"
        }
    }
    fun setLocationSelect(){
        val dialog = locationSettingDialogFragment
        dialog.show(supportFragmentManager, "LocationSelect")
    }

    private fun updateShelterListBasedOnTab(tabPosition: Int?) {
        viewModel.selectaddress.value?.let{address->
            val shelterType = getShelterType(tabPosition)
            viewModel.updateShelterList(this, "shelter_data.json", address, shelterType)
        }
    }


}