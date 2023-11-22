package com.daepiro.numberoneproject.presentation.view.family

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.daepiro.numberoneproject.R
import com.daepiro.numberoneproject.databinding.FragmentCheerDialogBinding
import com.daepiro.numberoneproject.databinding.FragmentFamilyDeleteDialogBinding
import com.daepiro.numberoneproject.databinding.FragmentSafetySendDialogBinding
import com.daepiro.numberoneproject.presentation.base.BaseDialogFragment
import com.daepiro.numberoneproject.presentation.util.Extensions.repeatOnStarted
import com.daepiro.numberoneproject.presentation.view.home.AroundShelterDetailFragmentArgs
import com.daepiro.numberoneproject.presentation.viewmodel.FamilyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class FamilyDeleteDialogFragment: BaseDialogFragment<FragmentFamilyDeleteDialogBinding>(R.layout.fragment_family_delete_dialog) {
    private val args by navArgs<FamilyDeleteDialogFragmentArgs>()
    val familyVM by viewModels<FamilyViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        this.isCancelable = false

        val originalText = getString(R.string._님을_삭제하시겠습니까_).format(args.familyInfo.realName)
        val spannableString = SpannableString(originalText)
        // 문자열에서 강조할 부분의 시작 인덱스 찾기
        val startIndex = originalText.indexOf(args.familyInfo.realName)

        spannableString.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.orange_500)),
            startIndex,
            startIndex + args.familyInfo.realName.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.tvTitle.text = spannableString

        binding.btnComplete.setOnClickListener {
            familyVM.deleteFamily(args.familyInfo.friendMemberId)
        }

        binding.btnClose.setOnClickListener {
            this.dismiss()
        }

    }

    override fun subscribeUi() {
        repeatOnStarted {
            familyVM.isCompleteDeleteFamily.collectLatest {
                if (it) {
                    findNavController().navigateUp()
                }
            }
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