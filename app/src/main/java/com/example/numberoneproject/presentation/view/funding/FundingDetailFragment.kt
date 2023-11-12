package com.example.numberoneproject.presentation.view.funding

import android.graphics.Paint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.doOnDetach
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.numberoneproject.R
import com.example.numberoneproject.databinding.FragmentFundingDetailBinding
import com.example.numberoneproject.presentation.base.BaseFragment
import com.example.numberoneproject.presentation.view.home.AroundShelterDetailFragmentArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt

@AndroidEntryPoint
class FundingDetailFragment : BaseFragment<FragmentFundingDetailBinding>(R.layout.fragment_funding_detail) {
    private val args by navArgs<FundingDetailFragmentArgs>()
    var globalListener: ViewTreeObserver.OnGlobalLayoutListener? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFundingPb()


        binding.btnFunding.setOnClickListener {
            SendTokenBottomSheet().show(parentFragmentManager, "")
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun setupInit() {
        binding.tvSponsorLink.paintFlags = Paint.UNDERLINE_TEXT_FLAG


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