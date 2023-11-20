package com.daepiro.numberoneproject.presentation.view.family

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.daepiro.numberoneproject.R
import com.daepiro.numberoneproject.databinding.FragmentCheerDialogBinding
import com.daepiro.numberoneproject.databinding.FragmentSafetySendDialogBinding
import com.daepiro.numberoneproject.presentation.base.BaseDialogFragment
import com.daepiro.numberoneproject.presentation.util.Extensions.repeatOnStarted
import com.daepiro.numberoneproject.presentation.util.Extensions.showToast
import com.daepiro.numberoneproject.presentation.viewmodel.FamilyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class SafetySendDialogFragment: BaseDialogFragment<FragmentSafetySendDialogBinding>(R.layout.fragment_safety_send_dialog) {
    private val args by navArgs<SafetySendDialogFragmentArgs>()
    val familyVM by viewModels<FamilyViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        this.isCancelable = false

        setArguments()

        binding.btnComplete.setOnClickListener {
            familyVM.postFamilySafety(args.familyInfo.friendMemberId)
        }

        binding.btnClose.setOnClickListener {
            this.dismiss()
        }

    }

    private fun setArguments() {
        binding.tvLocation.text = args.familyInfo.location
        binding.tvName.text = args.familyInfo.realName
        Glide.with(requireContext()).load(args.familyInfo.profileImageUrl).into(binding.ivProfile)

        if (args.familyInfo.session) {
            binding.ivOnlineState.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_online))
        } else {
            binding.ivOnlineState.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_offline))
        }


        if (args.familyInfo.isSafety) {
            binding.tvSafetyState.apply {
                text = getString(R.string.안전해요)
                setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
            }
            binding.ivSafetyState.apply {
                setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_good))
                imageTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.green))
            }
            binding.llSafetyState.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.light_green))
        } else {
            binding.tvSafetyState.apply {
                text = getString(R.string.현재_위험_지역에_있어요_안부를_물어보세요_)
                setTextColor(ContextCompat.getColor(requireContext(), R.color.warning))
            }
            binding.ivSafetyState.apply {
                setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_warning))
                imageTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.warning))
            }
            binding.llSafetyState.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.light_waring))
        }
    }

    override fun subscribeUi() {
        repeatOnStarted {
            familyVM.isCompletePostSafety.collectLatest {
                if (it) {
                    showToast("가족에게 안부를 물었습니다!")

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