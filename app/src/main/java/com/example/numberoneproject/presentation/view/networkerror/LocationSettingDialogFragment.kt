package com.example.numberoneproject.presentation.view.networkerror

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.numberoneproject.R
import com.example.numberoneproject.databinding.FragmentLocationSettingDialogBinding
import com.example.numberoneproject.presentation.base.BaseDialogFragment
import com.example.numberoneproject.presentation.viewmodel.CheckShelterViewModel
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationSettingDialogFragment : BaseDialogFragment<FragmentLocationSettingDialogBinding>(R.layout.fragment_location_setting_dialog) {
    private val viewModel: CheckShelterViewModel by activityViewModels()

    interface OnItemSelectedListener{
        fun onItemSelected()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }

    override fun onStart() {
        super.onStart()
        val window = dialog?.window
        val displayMetrics = context?.resources?.displayMetrics
        window?.let{w->
            displayMetrics?.let{metrics->
                val width = (metrics.widthPixels * 0.78).toInt()
                val height = (metrics.heightPixels * 0.59).toInt()
                w.setLayout(width,height)
                w.setGravity(Gravity.CENTER)
            }
        }
    }
    //선택된 아이템 저장 리스트
    var selectedItems = mutableMapOf<Int,String>()
    val selectedPosition = HashMap<Int,Int>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        setupInit()
        val recyclerview = binding.recyclerList
        val adapter = LocationAdapter(emptyList())
        recyclerview.adapter= adapter
        setList(R.array.all)



        binding.tabLayout.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                //먼저 아이템 선택되었는지 확인
                if(tab != null && tab.position !=0 && selectedItems[0].isNullOrEmpty()){
                    binding.tabLayout.getTabAt(0)?.select()
                    return
                }
                tab?.let{selectTab->
                    val resourceName = when(selectTab.position){
                        0-> "all"
                        1-> selectedItems[0] ?: "all"
                        else->{
                            (0 until selectTab.position)
                                .mapNotNull { selectedItems[it] }
                                .joinToString (separator = "_")
                        }
                    }
                    val resourceId = resources.getIdentifier(resourceName,"array",requireContext().packageName)
                    if(resourceId >0){
                        setList(resourceId)
                    }else{
                        Log.e("onTabSelected","invalid resource id")
                    }
                    for(i in selectTab.position +1 until binding.tabLayout.tabCount){
                        val isTabAble = selectedItems.containsKey(i-1)
                        binding.tabLayout.getTabAt(i)?.view?.isClickable = isTabAble
                    }
                }
                Log.d("onViewCreated","$selectedItems")
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.let{
                    val previousTabIndex = it.position-1
                    if(previousTabIndex >=0){
                        updateListForSelectedTab(previousTabIndex)
                    }else{
                        setList(R.array.all)
                    }
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })

        adapter.itemClickListener = {position, value ->
            //현재 탭의 위치 얻음
            val currentTabPosition = binding.tabLayout.selectedTabPosition
            selectedItems[currentTabPosition] = value
            selectedPosition[currentTabPosition] = position
            updateListForSelectedTab(currentTabPosition)
            moveToNextTab(currentTabPosition,value)
        }
    }

    override fun setupInit() {
        super.setupInit()
        binding.closeBtn.setOnClickListener{
            dismiss()
        }
        binding.complete.setOnClickListener{
            viewModel._setUpdate.value = true
            selectedItems.clear()
            viewModel._isactive.value = false
            dismiss()
        }
    }

    private fun setList(resourceId:Int){
        val localList = resources.getStringArray(resourceId).toList()
        (binding.recyclerList.adapter as LocationAdapter).updateList(localList)
    }
    private fun moveToNextTab(currentTab:Int,value:String){
        selectedItems[currentTab] = value
        updateListForSelectedTab(currentTab)
        if(currentTab < binding.tabLayout.tabCount-1){
            binding.tabLayout.getTabAt(currentTab +1)?.apply {
                view?.isClickable = true
                select()
            }
        }
        else{
            //마지막 선택시
            var locationString = listOfNotNull(selectedItems[0],selectedItems[1],selectedItems[2]).joinToString(separator = " ")
            Log.d("moveToNextTab","$locationString")
            viewModel._selectaddress.value = locationString
            viewModel._isactive.value = true
        }
    }
    private fun updateListForSelectedTab(position:Int){
        val resourceNameBuilder = StringBuilder()
        for(i in 0..position){
            selectedItems[i]?.let{
                if(resourceNameBuilder.isNotEmpty()){
                    resourceNameBuilder.append(it)
                }else{
                    resourceNameBuilder.append(it)
                }
            }
        }
        val resourceName=resourceNameBuilder.toString()
        val resourceId = if(resourceName.isNotEmpty()){
            resources.getIdentifier(resourceName, "array", requireContext().packageName)
        }else{
            //못넘어가도록 해야함
            R.array.all
        }
        if(resourceId != 0){
            setList(resourceId)
        }else{
            Log.e("updateListForSelectedTab","$resourceName")
            Log.e("updateListForSelectedTab", "invalid resource id")
        }
    }
}


