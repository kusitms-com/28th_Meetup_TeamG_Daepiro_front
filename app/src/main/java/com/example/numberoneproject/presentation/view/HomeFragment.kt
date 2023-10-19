package com.example.numberoneproject.presentation.view

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.numberoneproject.R
import com.example.numberoneproject.databinding.FragmentHomeBinding
import com.example.numberoneproject.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvTop.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToSubFragment()
            findNavController().navigate(action)
        }



    }
}