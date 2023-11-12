package com.example.numberoneproject.presentation.view.funding

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.numberoneproject.R
import com.example.numberoneproject.databinding.FragmentFundingBinding
import com.example.numberoneproject.presentation.base.BaseFragment
import com.example.numberoneproject.presentation.util.Extensions.repeatOnStarted
import com.example.numberoneproject.presentation.viewmodel.FundingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import okhttp3.internal.format

@AndroidEntryPoint
class FundingFragment : BaseFragment<FragmentFundingBinding>(R.layout.fragment_funding) {
    val fundingVM by viewModels<FundingViewModel>()
    private lateinit var cheerMessageAdapterFirst: CheerMessageAdapter
    private lateinit var cheerMessageAdapterSecond: CheerMessageAdapter
    private lateinit var fundingListAdapter: FundingListAdapter

    private lateinit var autoScrollRunnableFirst: AutoScrollRunnable
    private lateinit var autoScrollRunnableSecond: AutoScrollRunnable
    private val SCROLL_AMOUNT = 10 // 한 번에 이동할 픽셀 수
    private val SCROLL_DELAY: Long = 100 // 0.1초 (100 밀리초)마다 스크롤

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fundingVM = fundingVM

        setCheerMessageBanner()
        setFundingList()


        binding.cgFundingCategory.setOnCheckedStateChangeListener { group, checkedIds ->
            if (R.id.chip_funding_latest in checkedIds) {
                fundingVM.getFundingList("latest")
            } else if (R.id.chip_funding_poopular in checkedIds) {
                fundingVM.getFundingList("popular")
            }
        }

    }

    override fun setupInit() {
        fundingVM.getFundingList("latest")
    }

    private fun setCheerMessageBanner() {
        cheerMessageAdapterFirst = CheerMessageAdapter()
        cheerMessageAdapterSecond = CheerMessageAdapter()

        binding.rvCheerMessage.apply {
            layoutManager = LinearLayoutManager(requireContext(), GridLayoutManager.HORIZONTAL, false)
            adapter = cheerMessageAdapterFirst
            scrollToPosition(Int.MAX_VALUE / 2)
        }

//        val list = listOf("시지1","응원메지2","응원3","응원메시지4","응원시지5","응원메시지6","응원메시지7")
//        cheerMessageAdapterFirst.setData(list)

        binding.rvCheerMessage2.apply {
            layoutManager = LinearLayoutManager(requireContext(), GridLayoutManager.HORIZONTAL, false)
            adapter = cheerMessageAdapterSecond
            scrollToPosition(Int.MAX_VALUE / 2)
        }

//        val list2 = listOf("0","1","2","3","4","응원메시지6","응원메시지7")
//        cheerMessageAdapterSecond.setData(list2)

        autoScrollRunnableFirst = AutoScrollRunnable(binding.rvCheerMessage, SCROLL_AMOUNT, SCROLL_DELAY)
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
            override fun onClickItem(v: View, position: Int, sponsorId: Int) {
                val action = FundingFragmentDirections.actionFundingFragmentToFundingDetailFragment(sponsorId = sponsorId)
                findNavController().navigate(action)
            }
        })
    }

    override fun subscribeUi() {
        repeatOnStarted {
            fundingVM.fundingList.collectLatest {
                binding.tvSupporterCnt.text = getString(R.string.대피로와_함께_명이_후원에_참여했어요_).format(it.supporterCnt.toString())

                val originalText = getString(R.string._님의_가치있는_후원_대피로와_함께하세요_).format(it.nickname)
                val spannableString = SpannableString(originalText)
                // 문자열에서 강조할 부분의 시작 인덱스 찾기
                val startIndex = originalText.indexOf(it.nickname)

                spannableString.setSpan(
                    ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.orange_500)),
                    startIndex,
                    startIndex + it.nickname.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                binding.tvDescription.text = spannableString

                cheerMessageAdapterFirst.setData(it.messages.subList(0, it.messages.size/2))
                cheerMessageAdapterSecond.setData(it.messages.subList(it.messages.size/2, it.messages.size))

                fundingListAdapter.setData(it.sponsors)
            }
        }
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