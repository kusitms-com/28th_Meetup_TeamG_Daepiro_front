package com.daepiro.numberoneproject.presentation.view.funding.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.daepiro.numberoneproject.R
import com.daepiro.numberoneproject.data.model.SupportRequest
import com.daepiro.numberoneproject.databinding.FragmentSendHeartBottomSheetBinding
import com.daepiro.numberoneproject.presentation.util.Extensions.repeatOnStarted
import com.daepiro.numberoneproject.presentation.util.Extensions.showToast
import com.daepiro.numberoneproject.presentation.viewmodel.FundingViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import games.moisoni.google_iab.BillingConnector
import games.moisoni.google_iab.BillingEventListener
import games.moisoni.google_iab.enums.ProductType
import games.moisoni.google_iab.models.BillingResponse
import games.moisoni.google_iab.models.ProductInfo
import games.moisoni.google_iab.models.PurchaseInfo
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class SendHeartBottomSheet: BottomSheetDialogFragment() {
    private val args by navArgs<SendHeartBottomSheetArgs>()
    private lateinit var binding: FragmentSendHeartBottomSheetBinding
    val fundingVM by viewModels<FundingViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_send_heart_bottom_sheet, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.sheet = this
        binding.viewmodel = fundingVM
        binding.lifecycleOwner = this
        this.isCancelable = false

        initView()

        binding.ivClose.setOnClickListener {
            this.dismiss()
        }

        repeatOnStarted {
            fundingVM.supportResult.collectLatest {
                if (it != -1) {
                    showToast("후원이 완료되었습니다.")

                    val action = SendHeartBottomSheetDirections.actionSendHeartBottomSheetToCheerDialogFragment(sponsorId = args.sponsorId)
                    findNavController().navigate(action)
                }
            }
        }
    }

    private fun initView() {
        binding.tvTitle.text = args.title

        fundingVM.getUserHeartCnt()
    }

    fun onClickView(view: View) {
        when (view.id) {
            R.id.btn_minus -> if (fundingVM.selectedHeartCount.value > 0) fundingVM.selectedHeartCount.value = fundingVM.selectedHeartCount.value - 1
            R.id.btn_plus -> fundingVM.selectedHeartCount.value = fundingVM.selectedHeartCount.value + 1

            R.id.tv_reset -> fundingVM.selectedHeartCount.value = 0
            R.id.tv_heart_5 -> fundingVM.selectedHeartCount.value = fundingVM.selectedHeartCount.value + 5
            R.id.tv_heart_10 -> fundingVM.selectedHeartCount.value = fundingVM.selectedHeartCount.value + 10
            R.id.tv_heart_20 -> fundingVM.selectedHeartCount.value = fundingVM.selectedHeartCount.value + 20
            R.id.tv_heart_all -> fundingVM.selectedHeartCount.value = fundingVM.heartCnt.value

            R.id.btn_heart_charge -> {
                this@SendHeartBottomSheet.dismiss()

                val action = SendHeartBottomSheetDirections.actionSendHeartBottomSheetToHeartChargeFragment()
                findNavController().navigate(action)
            }

            R.id.btn_heart_send -> {
                if (fundingVM.heartCnt.value < fundingVM.selectedHeartCount.value) {
                    showToast("보유 마음 갯수가 부족합니다.")
                } else {
                    if (fundingVM.selectedHeartCount.value == 0) {
                        showToast("후원할 마음 갯수를 선택해주세요.")
                    } else {
                        fundingVM.postSupport(SupportRequest(args.sponsorId, fundingVM.selectedHeartCount.value))
                    }
                }
            }

        }
    }
}