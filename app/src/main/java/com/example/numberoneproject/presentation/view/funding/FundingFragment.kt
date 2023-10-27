package com.example.numberoneproject.presentation.view.funding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.numberoneproject.R
import com.example.numberoneproject.databinding.FragmentFundingBinding
import com.example.numberoneproject.presentation.base.BaseFragment
import com.example.numberoneproject.presentation.viewmodel.FundingViewModel

class FundingFragment : BaseFragment<FragmentFundingBinding>(R.layout.fragment_funding) {
    val fundingVM by viewModels<FundingViewModel>()
    private lateinit var fundingCategoryAdapter: FundingCategoryAdapter
    private lateinit var fundingListAdapter: FundingListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRv()
    }

    private fun setupRv() {
        fundingCategoryAdapter = FundingCategoryAdapter()
        fundingListAdapter = FundingListAdapter()

        binding.rvFundingCategory.apply {
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false)
            adapter = fundingCategoryAdapter
            setHasFixedSize(true)
        }

        fundingCategoryAdapter.setItemClickListener(object : FundingCategoryAdapter.OnItemClickListener {
            override fun onClickItem(v: View, position: Int, selectedPosition: Int) {

            }
        })


        binding.rvFundingList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = fundingListAdapter
            setHasFixedSize(true)
        }

        fundingListAdapter.setItemClickListener(object : FundingListAdapter.OnItemClickListener {
            override fun onClickItem(v: View, position: Int) {
                val action = FundingFragmentDirections.actionFundingFragmentToFundingDetailFragment()
                findNavController().navigate(action)
            }
        })
    }
}