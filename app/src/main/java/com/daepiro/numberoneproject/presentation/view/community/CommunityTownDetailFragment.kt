package com.daepiro.numberoneproject.presentation.view.community

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.daepiro.numberoneproject.R
import com.daepiro.numberoneproject.databinding.FragmentCommunityTownDetailBinding
import com.daepiro.numberoneproject.presentation.base.BaseFragment
import com.daepiro.numberoneproject.presentation.util.Extensions.repeatOnStarted
import com.daepiro.numberoneproject.presentation.view.MainActivity
import com.daepiro.numberoneproject.presentation.viewmodel.CommunityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class CommunityTownDetailFragment : BaseFragment<FragmentCommunityTownDetailBinding>(R.layout.fragment_community_town_detail) {
    val viewModel by activityViewModels<CommunityViewModel>()
    private lateinit var adapter:CommunityTownDetailImageAdapter
    private lateinit var adapterReply : CommunityTownDetailReplyAdapter
    private lateinit var adapterRereply:CommunityTownDetailRereplyAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        adapter = CommunityTownDetailImageAdapter(emptyList())
        binding.images.adapter = adapter

        binding.backBtn.setOnClickListener{
            findNavController().popBackStack()
        }
        collectImage()
    }
    //사진 데이터 감지
    private fun collectImage(){
        repeatOnStarted {
            viewModel.townDetail.collect{response->
                if(response.imageUrls == null){
                    viewModel._isVisible.value = false
                }
                response.imageUrls?.let {
                    adapter.updateList(it)
                    viewModel._isVisible.value = true
                }
            }
        }
    }



}