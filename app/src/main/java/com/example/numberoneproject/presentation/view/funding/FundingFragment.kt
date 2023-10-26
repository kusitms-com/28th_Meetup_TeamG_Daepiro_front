package com.example.numberoneproject.presentation.view.funding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.numberoneproject.R
import com.example.numberoneproject.databinding.FragmentFundingBinding
import com.example.numberoneproject.presentation.base.BaseFragment

class FundingFragment : BaseFragment<FragmentFundingBinding>(R.layout.fragment_funding) {
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

        binding.rvFundingList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = fundingListAdapter
            setHasFixedSize(true)
        }

        fundingListAdapter.setItemClickListener(object : FundingListAdapter.OnItemClickListener {
            override fun onItemClick(v: View, position: Int) {
                val action = FundingFragmentDirections.actionFundingFragmentToFundingDetailFragment()
                findNavController().navigate(action)
            }
        })
    }
}