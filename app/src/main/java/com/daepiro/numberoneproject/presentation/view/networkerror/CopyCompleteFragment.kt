package com.daepiro.numberoneproject.presentation.view.networkerror

import android.os.Bundle
import android.view.Gravity
import android.view.View
import com.daepiro.numberoneproject.R
import com.daepiro.numberoneproject.databinding.FragmentCopyCompleteBinding
import com.daepiro.numberoneproject.presentation.base.BaseDialogFragment

class CopyCompleteFragment : BaseDialogFragment<FragmentCopyCompleteBinding>(R.layout.fragment_copy_complete) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupInit()

    }

    override fun onStart() {
        super.onStart()
        val window = dialog?.window ?: return
        val params = window.attributes

        params.width = (resources.displayMetrics.widthPixels * 0.84).toInt()
        window.attributes = params

        window.setGravity(Gravity.CENTER)
    }

    override fun setupInit() {
        super.setupInit()
        binding.closeBtn.setOnClickListener{
            dismiss()
        }
    }

}