package com.example.numberoneproject.presentation.view.funding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.numberoneproject.R
import com.example.numberoneproject.databinding.FragmentCheerDialogBinding
import com.example.numberoneproject.presentation.base.BaseDialogFragment
import javax.inject.Inject

class CheerDialogFragment @Inject constructor(

): BaseDialogFragment<FragmentCheerDialogBinding>(R.layout.fragment_cheer_dialog) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.icClose.setOnClickListener {
            this.dismiss()
        }

        this.isCancelable = false
    }
}