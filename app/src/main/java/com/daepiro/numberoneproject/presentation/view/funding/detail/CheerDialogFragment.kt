package com.daepiro.numberoneproject.presentation.view.funding.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.daepiro.numberoneproject.R
import com.daepiro.numberoneproject.data.model.CheerMessageRequest
import com.daepiro.numberoneproject.databinding.FragmentCheerDialogBinding
import com.daepiro.numberoneproject.presentation.base.BaseDialogFragment
import com.daepiro.numberoneproject.presentation.util.Extensions.showToast
import com.daepiro.numberoneproject.presentation.viewmodel.FundingViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CheerDialogFragment: BaseDialogFragment<FragmentCheerDialogBinding>(R.layout.fragment_cheer_dialog) {
    private val args by navArgs<CheerDialogFragmentArgs>()
    val fundingVM by viewModels<FundingViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.isCancelable = false

        binding.btnComplete.setOnClickListener {
            if (binding.etMessage.text.isNullOrEmpty()) {
                showToast("응원메시지를 입력해 주세요.")
            } else {
                fundingVM.postCheerMessage(
                    supportId = args.sponsorId,
                    body = CheerMessageRequest(binding.etMessage.text.toString())
                )
                findNavController().navigateUp()
            }
        }

        binding.btnPass.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}