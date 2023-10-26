package com.example.numberoneproject.presentation.view.networkerror

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.numberoneproject.R
import com.example.numberoneproject.databinding.FragmentLocationSettingDialogBinding
import com.example.numberoneproject.presentation.base.BaseDialogFragment

class LocationSettingDialogFragment : BaseDialogFragment<FragmentLocationSettingDialogBinding>(R.layout.fragment_location_setting_dialog) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_location_setting_dialog, container, false)
    }

}