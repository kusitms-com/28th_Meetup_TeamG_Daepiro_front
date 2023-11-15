package com.daepiro.numberoneproject.presentation.view.funding.detail

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.doOnDetach
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.daepiro.numberoneproject.R
import com.daepiro.numberoneproject.databinding.FragmentFundingDetailBinding
import com.daepiro.numberoneproject.presentation.base.BaseFragment
import com.daepiro.numberoneproject.presentation.util.Extensions.repeatOnStarted
import com.daepiro.numberoneproject.presentation.viewmodel.FundingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlin.math.roundToInt

@AndroidEntryPoint
class FundingDetailFragment : BaseFragment<FragmentFundingDetailBinding>(R.layout.fragment_funding_detail) {
    private val args by navArgs<FundingDetailFragmentArgs>()
    val fundingVM by viewModels<FundingViewModel>()
    var globalListener: ViewTreeObserver.OnGlobalLayoutListener? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fundingVM = fundingVM

        fundingVM.getFundingDetail(args.sponsorId)

        binding.tvSponsorLink.setOnClickListener {
            val intent = Intent(requireContext(), WebViewActivity::class.java)
            intent.putExtra("url", fundingVM.fundingDetail.value.sponsorUrl)
            startActivity(intent)
        }

        binding.btnFunding.setOnClickListener {
            SendHeartBottomSheet(args.sponsorId, fundingVM.fundingDetail.value.title).show(parentFragmentManager, "")
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun setupInit() {
        binding.tvSponsorLink.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        setFundingPb()

    }

    override fun subscribeUi() {
        repeatOnStarted {
            fundingVM.fundingDetail.collectLatest {
                binding.tvProgress.text = "${(it.currentHeart * 100 / it.targetHeart)}%"
            }
        }
    }

    private fun setFundingPb() {
        var progressBarWidth = 0

        globalListener = ViewTreeObserver.OnGlobalLayoutListener {
            progressBarWidth = binding.pbFunding.width

            val newMarginLeft = (binding.pbFunding.progress * progressBarWidth / binding.pbFunding.max).toFloat()
            val params = binding.ivChar.layoutParams as ConstraintLayout.LayoutParams

            params.leftMargin = if (newMarginLeft < 100) {
                newMarginLeft.roundToInt()
            } else if (newMarginLeft > 800) {
                newMarginLeft.roundToInt() - 90
            } else {
                newMarginLeft.roundToInt() - 25
            }
            binding.ivChar.layoutParams = params
        }

        binding.pbFunding.viewTreeObserver.addOnGlobalLayoutListener(globalListener)

        /** 이게 왜 됨....?? 제거하면 뒤로가기 튕김... **/
        binding.pbFunding.doOnDetach { view ->
            globalListener?.let {
                view.viewTreeObserver?.removeOnGlobalLayoutListener(it)
            }
            globalListener = null
        }
    }

    override fun onResume() {
        super.onResume()
        fundingVM.getFundingDetail(args.sponsorId)
    }
}