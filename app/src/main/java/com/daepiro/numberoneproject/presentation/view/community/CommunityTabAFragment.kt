package com.daepiro.numberoneproject.presentation.view.community

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.daepiro.numberoneproject.R
import com.daepiro.numberoneproject.databinding.FragmentCommunityTabABinding
import com.daepiro.numberoneproject.presentation.base.BaseFragment
import com.daepiro.numberoneproject.presentation.util.Extensions.repeatOnStarted
import com.daepiro.numberoneproject.presentation.viewmodel.CommunityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommunityTabAFragment : BaseFragment<FragmentCommunityTabABinding>(R.layout.fragment_community_tab_a) {
    val viewModel by activityViewModels<CommunityViewModel>()
    private lateinit var mainAdapter:DisasterCommunityMainAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        binding.viewModel = viewModel
        viewModel.getDisasterHome()

    }

    override fun setupInit() {
        super.setupInit()
        setUpRecyclerView()
    }

    override fun subscribeUi() {
        super.subscribeUi()
        collectDisasterHomeData()
    }

    private fun setUpRecyclerView(){
        mainAdapter = DisasterCommunityMainAdapter(requireContext(),emptyList(),object : DisasterCommunityMainAdapter.onItemClickListener{
            override fun onItemClickListener(disasterId: Int) {
                viewModel.getDisasterDetail("time", disasterId)
                showBottomSheet()
            }

        })
        binding.mainRecycler.adapter = mainAdapter
    }


    private fun collectDisasterHomeData(){
        repeatOnStarted {
            viewModel.disasterHome.collect { response ->
                if (response != null) {
                    mainAdapter.updateList(response.situations)
                    viewModel._isLoading.value = false
                } else {
                    viewModel._isLoading.value = true
                    Log.d("collectDisasterHomeData", "Response is null")
                }
            }
        }
    }

    private fun showBottomSheet(){
        val dialog = CommunityTabABottomSheetFragment()
        dialog.show(requireActivity().supportFragmentManager, "dialogTag")
    }


}