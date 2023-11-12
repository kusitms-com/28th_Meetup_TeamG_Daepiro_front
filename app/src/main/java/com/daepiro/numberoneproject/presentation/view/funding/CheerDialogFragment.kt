package com.daepiro.numberoneproject.presentation.view.funding

import android.os.Bundle
import android.view.View
import com.daepiro.numberoneproject.R
import com.daepiro.numberoneproject.databinding.FragmentCheerDialogBinding
import com.daepiro.numberoneproject.presentation.base.BaseDialogFragment
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