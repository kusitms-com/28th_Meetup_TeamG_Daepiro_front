package com.daepiro.numberoneproject.presentation.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.daepiro.numberoneproject.R
import com.daepiro.numberoneproject.data.model.ShelterRequestBody
import com.daepiro.numberoneproject.databinding.FragmentAlarmDetailBinding
import com.daepiro.numberoneproject.presentation.base.BaseFragment
import com.daepiro.numberoneproject.presentation.util.Extensions.repeatOnStarted
import com.daepiro.numberoneproject.presentation.viewmodel.AlarmViewModel
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class AlarmDetailFragment: BaseFragment<FragmentAlarmDetailBinding>(R.layout.fragment_alarm_detail) {
    val alarmVM by viewModels<AlarmViewModel>()
    private lateinit var disasterAdapter: AlarmDisasterAdapter
    private lateinit var newsAdapter: AlarmNewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        disasterAdapter = AlarmDisasterAdapter()
        newsAdapter = AlarmNewsAdapter()

        binding.rvAlarmList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = disasterAdapter
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        alarmVM.getAlarmList(true)
        binding.tlCategory.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position) {
                    0 -> {
                        alarmVM.selectedTab.value = 0
                        binding.rvAlarmList.adapter = disasterAdapter
                    }
                    1 -> {
                        alarmVM.selectedTab.value = 1
                        binding.rvAlarmList.adapter = newsAdapter
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) { }
            override fun onTabReselected(tab: TabLayout.Tab?) { }
        })
    }

    override fun subscribeUi() {
        repeatOnStarted {
            alarmVM.selectedTab.collectLatest {
                if (it == 0) {
                    alarmVM.getAlarmList(true)
                } else {
                    alarmVM.getAlarmList(false)
                }
            }
        }
        repeatOnStarted {
            alarmVM.alarmList.collectLatest {
                if (alarmVM.selectedTab.value == 0) {
                    disasterAdapter.setData(it)
                } else {
                    newsAdapter.setData(it)
                }
            }
        }
    }

}