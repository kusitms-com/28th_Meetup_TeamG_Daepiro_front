package com.daepiro.numberoneproject.presentation.view.community

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.daepiro.numberoneproject.R
import com.daepiro.numberoneproject.databinding.FragmentCommunityTabBBinding
import com.daepiro.numberoneproject.presentation.base.BaseFragment
import com.daepiro.numberoneproject.presentation.viewmodel.CommunityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommunityTabBFragment : BaseFragment<FragmentCommunityTabBBinding>(R.layout.fragment_community_tab_b) {
    val viewModel by viewModels<CommunityViewModel>()
    private lateinit var adapter : TownCommentListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        adapter = TownCommentListAdapter(emptyList(), object :TownCommentListAdapter.onItemClickListener{
            override fun onItemClick(id: Int) {

            }

        })
        binding.recycler.adapter = adapter

        viewModel.getTownCommentList(null,null,5)

        viewModel.data.observe(viewLifecycleOwner){data->
            Log.d("CommunityForTownViewModelsuccess","${data}")
            adapter.updateList(data.content)
        }
        binding.all.setOnClickListener{
            binding.all.isSelected = !binding.all.isSelected
        }

    }

}