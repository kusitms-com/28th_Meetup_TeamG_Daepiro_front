package com.example.numberoneproject.presentation.view.funding

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.numberoneproject.R
import com.example.numberoneproject.databinding.FragmentFundingBinding
import com.example.numberoneproject.presentation.base.BaseFragment
import com.example.numberoneproject.presentation.viewmodel.FundingViewModel
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent

class FundingFragment : BaseFragment<FragmentFundingBinding>(R.layout.fragment_funding) {
    val fundingVM by viewModels<FundingViewModel>()
    private lateinit var cheerMessageAdapterFirst: CheerMessageAdapter
    private lateinit var cheerMessageAdapterSecond: CheerMessageAdapter
    private lateinit var fundingListAdapter: FundingListAdapter

    private val scrollHandler = Handler(Looper.getMainLooper())
    private lateinit var autoScrollRunnableFirst: AutoScrollRunnable
    private lateinit var autoScrollRunnableSecond: AutoScrollRunnable
    private val SCROLL_AMOUNT = 10 // 한 번에 이동할 픽셀 수
    private val SCROLL_DELAY: Long = 100 // 0.1초 (100 밀리초)마다 스크롤

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setCheerMessageBanner()
        setFundingList()

        binding.cgFundingCategory.setOnCheckedStateChangeListener { group, checkedIds ->
            if (R.id.chip_funding_latest in checkedIds) {

            } else if (R.id.chip_funding_poopular in checkedIds) {

            }
        }
    }

    private fun setCheerMessageBanner() {
        cheerMessageAdapterFirst = CheerMessageAdapter()
        cheerMessageAdapterSecond = CheerMessageAdapter()

        binding.rvCheerMessage.apply {
            layoutManager = LinearLayoutManager(requireContext(), GridLayoutManager.HORIZONTAL, false)
            adapter = cheerMessageAdapterFirst
            scrollToPosition(Int.MAX_VALUE / 2)
        }

        val list = listOf("시지1","응원메지2","응원3","응원메시지4","응원시지5","응원메시지6","응원메시지7")
        cheerMessageAdapterFirst.setData(list)

        autoScrollRunnableFirst = AutoScrollRunnable(binding.rvCheerMessage, SCROLL_AMOUNT, SCROLL_DELAY)


        binding.rvCheerMessage2.apply {
            layoutManager = LinearLayoutManager(requireContext(), GridLayoutManager.HORIZONTAL, false)
            adapter = cheerMessageAdapterSecond
            scrollToPosition(Int.MAX_VALUE / 2)
        }

        val list2 = listOf("0","1","2","3","4","응원메시지6","응원메시지7")
        cheerMessageAdapterSecond.setData(list2)

        autoScrollRunnableSecond = AutoScrollRunnable(binding.rvCheerMessage2, SCROLL_AMOUNT, SCROLL_DELAY)
    }

    private fun setFundingList() {
        fundingListAdapter = FundingListAdapter()

        binding.rvFundingList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = fundingListAdapter
            setHasFixedSize(true)
        }

        fundingListAdapter.setItemClickListener(object : FundingListAdapter.OnItemClickListener {
            override fun onClickItem(v: View, position: Int) {
                val action = FundingFragmentDirections.actionFundingFragmentToFundingDetailFragment()
                findNavController().navigate(action)
            }
        })
    }

    inner class AutoScrollRunnable(
        private val recyclerView: RecyclerView,
        private val scrollAmount: Int,
        private val scrollDelay: Long
    ) : Runnable {
        override fun run() {
            recyclerView.smoothScrollBy(scrollAmount, 0)
            Handler().postDelayed(this, scrollDelay)
        }
    }

    override fun onStart() {
        super.onStart()
        startAutoScroll()
    }

    override fun onStop() {
        super.onStop()
        stopAutoScroll()
    }

    private fun startAutoScroll() {
        binding.rvCheerMessage.removeCallbacks(autoScrollRunnableFirst) // 이전의 콜백 제거
        binding.rvCheerMessage.post(autoScrollRunnableFirst) // AutoScrollRunnable 시작

        binding.rvCheerMessage2.removeCallbacks(autoScrollRunnableSecond) // 이전의 콜백 제거
        binding.rvCheerMessage2.post(autoScrollRunnableSecond) // AutoScrollRunnable 시작
    }

    private fun stopAutoScroll() {
        binding.rvCheerMessage.removeCallbacks(autoScrollRunnableFirst) // AutoScrollRunnable 중지
        binding.rvCheerMessage2.removeCallbacks(autoScrollRunnableSecond) // AutoScrollRunnable 중지
    }
}