package com.daepiro.numberoneproject.presentation.view.mypage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.daepiro.numberoneproject.R
import com.daepiro.numberoneproject.databinding.FragmentMypageBinding
import com.daepiro.numberoneproject.presentation.base.BaseFragment
import com.daepiro.numberoneproject.presentation.util.TokenManager
import com.daepiro.numberoneproject.presentation.viewmodel.FundingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MypageFragment: BaseFragment<FragmentMypageBinding>(R.layout.fragment_mypage) {
    val fundingVM by viewModels<FundingViewModel>()
    @Inject lateinit var tokenManager: TokenManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewmodel = fundingVM
        fundingVM.getUserHeartCnt()

        lifecycleScope.launch {
            val nickname = tokenManager.nickName.first()

            binding.tvNickname.text = nickname
        }

        binding.tvHeartCharge.setOnClickListener {
            val action = MypageFragmentDirections.actionMypageFragmentToHeartChargeFragment()
            findNavController().navigate(action)
        }
    }
}