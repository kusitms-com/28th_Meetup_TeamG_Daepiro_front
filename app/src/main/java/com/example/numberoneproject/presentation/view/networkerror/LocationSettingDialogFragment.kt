package com.example.numberoneproject.presentation.view.networkerror

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.WindowManager
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        var locationString:String=""
        setupInit()
        val recyclerview = binding.recyclerList
        val adapter = LocationAdapter(emptyList())
        recyclerview.adapter= adapter
        setList(R.array.all)
        val tabLayout = binding.tabLayout
        //선택된 아이템 저장 리스트
        val selectedItems = mutableListOf<String>()
        val selectedPosition = HashMap<Int,Int>()

        adapter.itemClickListener = {position, value ->
            //현재 탭의 위치 얻음
            val currentTabPosition = tabLayout.selectedTabPosition
            if(selectedItems.size > currentTabPosition){
                selectedItems[currentTabPosition] = value
            }
            else{
                selectedItems.add(value)
            }
            selectedPosition[currentTabPosition] = position
            adapter.selectItem(position)
            //아이템 선택시 다음탭으로 이동
            if(currentTabPosition < tabLayout.tabCount -1){
                val resourceName = selectedItems.joinToString(separator = "")
                val nextTab = tabLayout.getTabAt(currentTabPosition+1)
                nextTab?.select()
                val resourceId = resources.getIdentifier(resourceName, "array", requireContext().packageName)
                if(resourceId >0){
                    Log.d("LocationSetting","${viewModel.isactive.value}")
                    setList(resourceId)
                    val nextTabPosition = currentTabPosition +1
                    val selectedPositionInNextTab = selectedPosition[nextTabPosition] ?: -1
                    adapter.selectItem(selectedPositionInNextTab)
                }
            }
            //마지막 탭 아이템 선택시
            else{
                viewModel._isactive.value = true
                locationString = selectedItems.joinToString(separator = " ")
                viewModel._selectaddress.value = locationString
            }
        }
        binding.complete.setOnClickListener{
            (activity as? CheckShelterActivity)?.viewModel?.setSelectedAddress(locationString)
            viewModel._setUpdate.value = true
            dismiss()
        }
    }

    override fun setupInit() {
        super.setupInit()
        binding.closeBtn.setOnClickListener{
            dismiss()
        }

    }

    private fun setList(resourceId:Int){
        val localList = resources.getStringArray(resourceId).toList()
        (binding.recyclerList.adapter as LocationAdapter).updateList(localList)
    }
}