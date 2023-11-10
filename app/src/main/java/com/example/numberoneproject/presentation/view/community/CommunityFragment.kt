package com.example.numberoneproject.presentation.view.community

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.numberoneproject.R
import com.example.numberoneproject.databinding.FragmentCommunityBinding
import com.example.numberoneproject.presentation.base.BaseFragment
import com.example.numberoneproject.presentation.viewmodel.CommunityViewModel

class CommunityFragment : BaseFragment<FragmentCommunityBinding>(R.layout.fragment_community) {
    val viewModel by viewModels<CommunityViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun setupInit() {
        super.setupInit()
    }

}