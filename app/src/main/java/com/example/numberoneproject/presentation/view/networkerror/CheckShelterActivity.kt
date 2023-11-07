package com.example.numberoneproject.presentation.view.networkerror

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
    val viewModel by viewModels<CheckShelterViewModel>()
    private lateinit var adapter : ShelterListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = ShelterListAdapter()
        binding.recyclerList.adapter = adapter

        binding.viewModel = viewModel
        binding.includeAppBar.appBarText.text = "대피소 조회"

        val initialTabPosition = binding.tabLayout.selectedTabPosition.takeIf { it != -1 } ?: 0
        updateShelterListBasedOnTab(initialTabPosition)

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
        //앱이 시작시 초기 데이터로딩
        viewModel.selectaddress.value?.let{
            viewModel.updateShelterList(this, "shelter_data.json",it,"")
        }?:Log.e("checkshelter", "no load data")
        binding.touchContainer.setOnClickListener{
            setLocationSelect()
        }
    }

    private fun setupTabLayout(){
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
//                val address = viewModel.selectaddress.value
//                val shelterType = getShelterType(tab?.position)
//                if(address!!.isNotEmpty()){
//                    viewModel.updateShelterList(this@CheckShelterActivity,"shelter_data.json",address,shelterType)
//                }
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
        val dialog = LocationSettingDialogFragment()
        dialog.show(supportFragmentManager, "LocationSelect")
    }

//    private fun updateShelterList(address:String, shelterType:String){
//        viewModel.updateShelterList(this,"shelter_data.json",address,shelterType)
//    }
    private fun updateShelterListBasedOnTab(tabPosition: Int?) {
        val address = viewModel.selectaddress.value ?: ""
        val shelterType = getShelterType(tabPosition)
        viewModel.updateShelterList(this, "shelter_data.json", address, shelterType)
    }
}