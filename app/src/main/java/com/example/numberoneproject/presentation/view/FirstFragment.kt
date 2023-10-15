package com.example.numberoneproject.presentation.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.numberoneproject.R
import com.example.numberoneproject.databinding.FragmentFirstBinding
import com.example.numberoneproject.presentation.base.BaseFragment
import com.example.numberoneproject.presentation.util.Extensions.myLog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FirstFragment : BaseFragment<FragmentFirstBinding>(R.layout.fragment_first) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvDefault.setOnClickListener {
            val action = FirstFragmentDirections.actionFirstFragmentToSubFragment(number = 100)
            findNavController().navigate(action)

        }


    }
}