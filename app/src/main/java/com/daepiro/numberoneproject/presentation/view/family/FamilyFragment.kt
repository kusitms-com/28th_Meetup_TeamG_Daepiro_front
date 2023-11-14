package com.daepiro.numberoneproject.presentation.view.family

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.daepiro.numberoneproject.R
import com.daepiro.numberoneproject.databinding.FragmentFamilyBinding
import com.daepiro.numberoneproject.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FamilyFragment : BaseFragment<FragmentFamilyBinding>(R.layout.fragment_family) {
    private lateinit var familyListAdapter: FamilyListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFamilyRV()
    }

    private fun setFamilyRV() {
        familyListAdapter = FamilyListAdapter()

        binding.rvFamily.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = familyListAdapter
        }
    }
}