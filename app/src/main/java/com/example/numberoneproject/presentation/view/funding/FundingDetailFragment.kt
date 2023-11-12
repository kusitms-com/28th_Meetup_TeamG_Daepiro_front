package com.example.numberoneproject.presentation.view.funding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.numberoneproject.R
import com.example.numberoneproject.databinding.FragmentFundingDetailBinding
import com.example.numberoneproject.presentation.base.BaseFragment

class FundingDetailFragment : BaseFragment<FragmentFundingDetailBinding>(R.layout.fragment_funding_detail) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun setupInit() {
        binding.fragment = this@FundingDetailFragment

    }

    private fun setFundingPb() {
        var progressBarWidth = 0

        globalListener = ViewTreeObserver.OnGlobalLayoutListener {
            progressBarWidth = binding.pbFunding.width

            val newMarginLeft = (binding.pbFunding.progress * progressBarWidth / binding.pbFunding.max).toFloat()
            val params = binding.ivChar.layoutParams as ConstraintLayout.LayoutParams
            params.leftMargin = if (newMarginLeft <10) newMarginLeft.roundToInt() else newMarginLeft.roundToInt() - 20
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
}