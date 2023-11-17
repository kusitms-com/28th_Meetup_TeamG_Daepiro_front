package com.daepiro.numberoneproject.presentation.view.family

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.daepiro.numberoneproject.R
import com.daepiro.numberoneproject.databinding.FragmentFamilyBinding
import com.daepiro.numberoneproject.presentation.base.BaseFragment
import com.daepiro.numberoneproject.presentation.util.Extensions.repeatOnStarted
import com.daepiro.numberoneproject.presentation.util.Extensions.showToast
import com.daepiro.numberoneproject.presentation.view.funding.detail.CheerDialogFragment
import com.daepiro.numberoneproject.presentation.viewmodel.FamilyViewModel
import com.kakao.sdk.share.ShareClient
import com.kakao.sdk.template.model.Button
import com.kakao.sdk.template.model.Content
import com.kakao.sdk.template.model.FeedTemplate
import com.kakao.sdk.template.model.Link
import com.kakao.sdk.template.model.Social
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class FamilyFragment : BaseFragment<FragmentFamilyBinding>(R.layout.fragment_family) {
    val familyVM by viewModels<FamilyViewModel>()
    private lateinit var familyListAdapter: FamilyListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFamilyRV()

        binding.tvManage.setOnClickListener {
            familyListAdapter.changeManageMode()
            familyVM.isFamilyListManageMode.value = !familyVM.isFamilyListManageMode.value
        }

        binding.cdAddFamily.setOnClickListener {
            kakaoShare()
        }
    }

    private fun kakaoShare() {
        val defaultFeed = FeedTemplate(
            content = Content(
                title = String.format(getString(R.string._님이_대피로_가족으로_초대하셨어요_), "종석"),
                description = getString(R.string.가족_맺기를_수락하시면_서로의_안전_상태를_),
                imageUrl = "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2F6J5Im%2FbtsAqIjXTzo%2FtOBkJD8VpHuZtJBGfdBLjk%2Fimg.png",
                link = Link(
                    mobileWebUrl = "https://play.google.com/store/apps/details?id=com.daepiro.numberoneproject"
                )
            ),
            social = Social(
                likeCount = 11,
                commentCount = 17,
                sharedCount = 28
            ),
            buttons = listOf(
                Button(
                    getString(R.string.초대_수락하기),
                    Link(
                        androidExecutionParams = mapOf(
                            "userToken" to "23",
                            "number" to "1"
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
            override fun onClickItem(v: View, position: Int) {
                if (!familyVM.isFamilyListManageMode.value) {
                    SafetySendDialogFragment().show(parentFragmentManager, "")
                }
            }

            override fun onClickManage(v: View, position: Int) {
                if (familyVM.isFamilyListManageMode.value) {
                    FamilyDeleteDialogFragment().show(parentFragmentManager, "")
                }
            }
        })
    }

    override fun subscribeUi() {
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