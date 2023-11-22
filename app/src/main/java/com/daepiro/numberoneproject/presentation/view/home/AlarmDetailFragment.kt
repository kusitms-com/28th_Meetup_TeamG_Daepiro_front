package com.daepiro.numberoneproject.presentation.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.daepiro.numberoneproject.R
import com.daepiro.numberoneproject.data.model.ShelterRequestBody
import com.daepiro.numberoneproject.databinding.FragmentAlarmDetailBinding
import com.daepiro.numberoneproject.presentation.base.BaseFragment
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlarmDetailFragment: BaseFragment<FragmentAlarmDetailBinding>(R.layout.fragment_alarm_detail) {
    private lateinit var disasterAdapter: AlarmDisasterAdapter
    private lateinit var newsAdapter: AlarmNewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        disasterAdapter = AlarmDisasterAdapter()
        newsAdapter = AlarmNewsAdapter()

        binding.rvShelter.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = disasterAdapter
        }


        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.tlCategory.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position) {
                    0 -> {
                        binding.rvShelter.apply {
                            layoutManager = LinearLayoutManager(requireContext())
                            adapter = disasterAdapter
                        }
                    }
                    1 -> {
                        binding.rvShelter.apply {
                            layoutManager = LinearLayoutManager(requireContext())
                            adapter = newsAdapter
                        }
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) { }
            override fun onTabReselected(tab: TabLayout.Tab?) { }
        })
    }

}