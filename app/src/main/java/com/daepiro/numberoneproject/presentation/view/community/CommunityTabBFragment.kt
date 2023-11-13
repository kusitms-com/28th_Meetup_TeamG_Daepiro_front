package com.daepiro.numberoneproject.presentation.view.community

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.daepiro.numberoneproject.R
import com.daepiro.numberoneproject.databinding.FragmentCommunityBinding
import com.daepiro.numberoneproject.databinding.FragmentCommunityTabBBinding
import com.daepiro.numberoneproject.presentation.base.BaseFragment
import com.daepiro.numberoneproject.presentation.viewmodel.CommunityForTownViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommunityTabBFragment : BaseFragment<FragmentCommunityTabBBinding>(R.layout.fragment_community_tab_b){
    //val viewModel by viewModels<CommunityForTownViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding.viewModel = viewModel
//        viewModel.getCommunityTownList()
    }

}