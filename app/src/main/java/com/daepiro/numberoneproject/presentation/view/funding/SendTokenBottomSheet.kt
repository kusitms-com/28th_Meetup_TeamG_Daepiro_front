package com.daepiro.numberoneproject.presentation.view.funding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.daepiro.numberoneproject.R
import com.daepiro.numberoneproject.databinding.FragmentSendTokenBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SendTokenBottomSheet: BottomSheetDialogFragment() {
    private lateinit var binding: FragmentSendTokenBottomSheetBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_send_token_bottom_sheet, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.isCancelable = false

        binding.ivClose.setOnClickListener {
            this.dismiss()
        }

        binding.btnFunding.setOnClickListener {
            CheerDialogFragment().show(parentFragmentManager, "")
        }
    }
}