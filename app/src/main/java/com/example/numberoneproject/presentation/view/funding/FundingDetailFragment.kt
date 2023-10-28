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

    fun onClickGoBack(view: View) {
        findNavController().navigateUp()
    }
}