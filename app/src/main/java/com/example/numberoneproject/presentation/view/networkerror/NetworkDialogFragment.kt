package com.example.numberoneproject.presentation.view.networkerror

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.numberoneproject.R
import com.example.numberoneproject.databinding.FragmentNetworkDialogBinding
import com.example.numberoneproject.presentation.base.BaseDialogFragment

class NetworkDialogFragment : BaseDialogFragment<FragmentNetworkDialogBinding>(R.layout.fragment_network_dialog) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun setupInit() {
        super.setupInit()
        binding.actionBtn.setOnClickListener{

        }
        binding.shelterBtn.setOnClickListener{

        }
    }

}