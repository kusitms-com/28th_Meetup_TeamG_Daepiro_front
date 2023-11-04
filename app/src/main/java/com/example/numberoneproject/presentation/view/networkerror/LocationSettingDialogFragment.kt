package com.example.numberoneproject.presentation.view.networkerror

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.numberoneproject.R
import com.example.numberoneproject.databinding.FragmentLocationSettingDialogBinding
import com.example.numberoneproject.presentation.base.BaseDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationSettingDialogFragment : BaseDialogFragment<FragmentLocationSettingDialogBinding>(R.layout.fragment_location_setting_dialog) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupInit()

    }

    override fun setupInit() {
        super.setupInit()
        binding.closeBtn.setOnClickListener{
            dismiss()
        }
    }


}