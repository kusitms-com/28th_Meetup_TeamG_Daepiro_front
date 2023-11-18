package com.daepiro.numberoneproject.presentation.view.family

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.RequiresApi
import com.daepiro.numberoneproject.R
import com.daepiro.numberoneproject.databinding.FragmentCheerDialogBinding
import com.daepiro.numberoneproject.databinding.FragmentSafetySendDialogBinding
import com.daepiro.numberoneproject.presentation.base.BaseDialogFragment
import com.daepiro.numberoneproject.presentation.util.Extensions.showToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SafetySendDialogFragment: BaseDialogFragment<FragmentSafetySendDialogBinding>(R.layout.fragment_safety_send_dialog) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        this.isCancelable = false

        binding.btnComplete.setOnClickListener {
            showToast("안부 묻기")
        }

        binding.btnClose.setOnClickListener {
            this.dismiss()
        }

        binding.btnComplete

    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onResume() {
        super.onResume()
        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        val windowManager = requireActivity().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val size = windowManager.currentWindowMetrics
        val deviceWidth = size.bounds.width()

        params?.width = (deviceWidth * 0.9).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }

}