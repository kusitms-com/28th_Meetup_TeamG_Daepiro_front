package com.daepiro.numberoneproject.presentation.view.family

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.daepiro.numberoneproject.R
import com.daepiro.numberoneproject.data.model.FamilyListResponse
import com.daepiro.numberoneproject.databinding.FragmentFamilyBinding
import com.daepiro.numberoneproject.presentation.base.BaseFragment
import com.daepiro.numberoneproject.presentation.util.Extensions.repeatOnStarted
import com.daepiro.numberoneproject.presentation.util.Extensions.showToast
import com.daepiro.numberoneproject.presentation.util.TokenManager
import com.daepiro.numberoneproject.presentation.viewmodel.FamilyViewModel
import com.kakao.sdk.share.ShareClient
import com.kakao.sdk.template.model.Button
import com.kakao.sdk.template.model.Content
import com.kakao.sdk.template.model.FeedTemplate
import com.kakao.sdk.template.model.Link
import com.kakao.sdk.template.model.Social
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class FamilyFragment : BaseFragment<FragmentFamilyBinding>(R.layout.fragment_family) {
    val familyVM by viewModels<FamilyViewModel>()
    private lateinit var familyListAdapter: FamilyListAdapter
    @Inject lateinit var tokenManager: TokenManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        familyVM.getFamilyList()
        setFamilyRV()

        binding.tvManage.setOnClickListener {
            familyListAdapter.changeManageMode()
            familyVM.isFamilyListManageMode.value = !familyVM.isFamilyListManageMode.value
        }

        binding.cdAddFamily.setOnClickListener {
            lifecycleScope.launch {
                val myMemberId = tokenManager.memberId.first()

                withContext(Dispatchers.Main) {
                    kakaoShare(myMemberId)

                }
            }
        }
    }

    private fun kakaoShare(myMemberId: String) {
        val defaultFeed = FeedTemplate(
            content = Content(
                title = String.format(getString(R.string._님이_대피로_가족으로_초대하셨어요_), "종석"),
                description = getString(R.string.가족_맺기를_수락하시면_서로의_안전_상태를_),
                imageUrl = "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2F6J5Im%2FbtsAqIjXTzo%2FtOBkJD8VpHuZtJBGfdBLjk%2Fimg.png",
                link = Link(
                    mobileWebUrl = "https://play.google.com/store/apps/details?id=com.daepiro.numberoneproject"
                )
            ),
            buttons = listOf(
                Button(
                    getString(R.string.초대_수락하기),
                    Link(
                        androidExecutionParams = mapOf(
                            "memberId" to myMemberId
                        )
                    )
                )
            )
        )

        ShareClient.instance.shareDefault(requireContext(), defaultFeed) { sharingResult, error ->
            if (error != null) {
                showToast("공유에 실패했습니다.")
            } else {
                startActivity(sharingResult!!.intent)
            }
        }
    }

    private fun setFamilyRV() {
        familyListAdapter = FamilyListAdapter()

        binding.rvFamily.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = familyListAdapter
        }

        familyListAdapter.setItemClickListener(object : FamilyListAdapter.OnItemClickListener {
            override fun onClickItem(v: View, position: Int, familyInfo: FamilyListResponse) {
                if (!familyVM.isFamilyListManageMode.value) {
//                    SafetySendDialogFragment().show(parentFragmentManager, "")
                    val action = FamilyFragmentDirections.actionFamilyFragmentToSafetySendDialogFragment(familyInfo)
                    findNavController().navigate(action)
                }
            }

            override fun onClickManage(v: View, position: Int) {
                if (familyVM.isFamilyListManageMode.value) {
                    FamilyDeleteDialogFragment().show(parentFragmentManager, "")
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        familyVM.isFamilyListManageMode.value = false
    }

    override fun subscribeUi() {
        repeatOnStarted {
            familyVM.familyList.collectLatest {
                familyListAdapter.setData(it)
            }
        }
        repeatOnStarted {
            familyVM.isFamilyListManageMode.collectLatest {
                // 관리모드
                if (it) {
                    binding.tvManage.apply {
                        text = getString(R.string.완료)
                        setTextColor(ContextCompat.getColor(requireContext(), R.color.orange_400))
                    }
                } else {
                    binding.tvManage.apply {
                        text = getString(R.string.관리)
                        setTextColor(ContextCompat.getColor(requireContext(), R.color.secondary_400))
                    }
                }
            }
        }
    }
}