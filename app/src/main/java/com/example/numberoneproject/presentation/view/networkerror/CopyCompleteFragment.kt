package com.example.numberoneproject.presentation.view.networkerror

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.numberoneproject.R
import com.example.numberoneproject.databinding.FragmentCopyCompleteBinding
import com.example.numberoneproject.presentation.base.BaseDialogFragment

class CopyCompleteFragment : BaseDialogFragment<FragmentCopyCompleteBinding>(R.layout.fragment_copy_complete) {

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