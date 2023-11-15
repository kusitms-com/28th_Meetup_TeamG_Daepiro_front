package com.daepiro.numberoneproject.presentation.view.family

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.daepiro.numberoneproject.R
import com.daepiro.numberoneproject.databinding.FragmentFamilyBinding
import com.daepiro.numberoneproject.presentation.base.BaseFragment
import com.daepiro.numberoneproject.presentation.util.Extensions.showToast
import com.daepiro.numberoneproject.presentation.view.funding.detail.CheerDialogFragment
import com.kakao.sdk.share.ShareClient
import com.kakao.sdk.template.model.Button
import com.kakao.sdk.template.model.Content
import com.kakao.sdk.template.model.FeedTemplate
import com.kakao.sdk.template.model.Link
import com.kakao.sdk.template.model.Social
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FamilyFragment : BaseFragment<FragmentFamilyBinding>(R.layout.fragment_family) {
    private lateinit var familyListAdapter: FamilyListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFamilyRV()

        binding.cdAddFamily.setOnClickListener {
            kakaoShare()
        }
    }

    private fun kakaoShare() {
        val defaultFeed = FeedTemplate(
            content = Content(
                title = "대피로로 돔황챠 ~~~",
                description = "#지진 #수해 #전쟁 #칼부림 #기획 #디자인",
                imageUrl = "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FAFCSa%2FbtsAqzzFseG%2FtD5dchJEDuJ4k4C6JT038k%2Fimg.png",
                link = Link(
                    mobileWebUrl = "https://play.google.com/store/apps"
                )
            ),
            social = Social(
                likeCount = 286,
                commentCount = 45,
                sharedCount = 845
            ),
            buttons = listOf(
                Button(
                    "앱으로 보기",
                    Link(
                        androidExecutionParams = mapOf(
                            "key1" to "value1",
                            "key2" to "value2"
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
            layoutManager = LinearLayoutManager(requireContext())
            adapter = familyListAdapter
        }

        familyListAdapter.setItemClickListener(object : FamilyListAdapter.OnItemClickListener {
            override fun onClickItem(v: View, position: Int) {
                SafetySendDialogFragment().show(parentFragmentManager, "")
            }

        })
    }
}