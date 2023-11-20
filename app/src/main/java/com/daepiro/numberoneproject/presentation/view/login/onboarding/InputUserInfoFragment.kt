package com.daepiro.numberoneproject.presentation.view.login.onboarding

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.daepiro.numberoneproject.R
import com.daepiro.numberoneproject.databinding.FragmentInputUserInfoBinding
import com.daepiro.numberoneproject.presentation.base.BaseFragment
import com.daepiro.numberoneproject.presentation.viewmodel.OnboardingViewModel

class InputUserInfoFragment: BaseFragment<FragmentInputUserInfoBinding>(R.layout.fragment_input_user_info) {
    private val viewModel:OnboardingViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val originalText = getString(R.string.대피로에서_사용하실_닉네임을_설정해주세요_)
        val spannableString = SpannableString(originalText)
        // 문자열에서 강조할 부분의 시작 인덱스 찾기
        val startIndex = originalText.indexOf(getString(R.string.닉네임))
        val startIndex2 = originalText.indexOf(getString(R.string.이름))

        spannableString.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.orange_500)),
            startIndex,
            startIndex + getString(R.string.닉네임).length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.orange_500)),
            startIndex2,
            startIndex2 + getString(R.string.이름).length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.tvNickname.text = spannableString


        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnNext.setOnClickListener{
            val action = InputUserInfoFragmentDirections.actionInputUserInfoFragmentToSelectLocationFragment()
            findNavController().navigate(action)
        }
    }
}