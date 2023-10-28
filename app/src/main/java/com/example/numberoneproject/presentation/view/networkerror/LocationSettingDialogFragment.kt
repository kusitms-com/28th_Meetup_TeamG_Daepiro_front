package com.example.numberoneproject.presentation.view.networkerror

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.numberoneproject.R
import com.example.numberoneproject.databinding.FragmentLocationSettingDialogBinding
import com.example.numberoneproject.presentation.base.BaseDialogFragment
import org.json.JSONObject
import java.io.InputStream
import java.lang.Exception

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