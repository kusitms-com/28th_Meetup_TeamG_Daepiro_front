package com.example.numberoneproject.presentation.view.networkerror

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.viewModels
import com.example.numberoneproject.R
import com.example.numberoneproject.databinding.FragmentLocationSettingDialogBinding
import com.example.numberoneproject.presentation.base.BaseDialogFragment
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationSettingDialogFragment : BaseDialogFragment<FragmentLocationSettingDialogBinding>(R.layout.fragment_location_setting_dialog) {
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
        setupInit()
        val recyclerview = binding.recyclerList
        val adapter = LocationAdapter(emptyList())
        recyclerview.adapter= adapter
        setList(R.array.all)
        val tabLayout = binding.tabLayout

        adapter.itemClickListener = {position, value ->
            val currentTabPosition = tabLayout.selectedTabPosition
            if(currentTabPosition < tabLayout.tabCount -1){
                val nextTab = tabLayout.getTabAt(currentTabPosition+1)
                nextTab?.select()
                val resourceId = resources.getIdentifier(value, "array", requireContext().packageName)
                if(resourceId >0){
                    setList(resourceId)
                }
            }
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