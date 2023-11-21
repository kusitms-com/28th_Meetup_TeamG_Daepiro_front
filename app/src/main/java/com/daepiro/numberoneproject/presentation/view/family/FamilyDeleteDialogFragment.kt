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
import androidx.navigation.fragment.navArgs
import com.daepiro.numberoneproject.R
import com.daepiro.numberoneproject.databinding.FragmentCheerDialogBinding
import com.daepiro.numberoneproject.databinding.FragmentFamilyDeleteDialogBinding
import com.daepiro.numberoneproject.databinding.FragmentSafetySendDialogBinding
import com.daepiro.numberoneproject.presentation.base.BaseDialogFragment
import com.daepiro.numberoneproject.presentation.view.home.AroundShelterDetailFragmentArgs
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FamilyDeleteDialogFragment: BaseDialogFragment<FragmentFamilyDeleteDialogBinding>(R.layout.fragment_family_delete_dialog) {
    private val args by navArgs<FamilyDeleteDialogFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        this.isCancelable = false

        binding.tvTitle.text = String.format(getString(R.string._님을_삭제하시겠습니까_), args.familyInfo.realName)

        binding.btnComplete.setOnClickListener {
            
        }

        binding.btnClose.setOnClickListener {
            this.dismiss()
        }

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