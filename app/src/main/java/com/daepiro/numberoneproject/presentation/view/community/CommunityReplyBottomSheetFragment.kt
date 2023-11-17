package com.daepiro.numberoneproject.presentation.view.community

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.daepiro.numberoneproject.R
import com.daepiro.numberoneproject.databinding.FragmentCommunityReplyBottomSheetBinding
import com.daepiro.numberoneproject.presentation.viewmodel.CommunityViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CommunityReplyBottomSheetFragment : BottomSheetDialogFragment() {
    private val vieWModel: CommunityViewModel by activityViewModels()
    private var _binding:FragmentCommunityReplyBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCommunityReplyBottomSheetBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = vieWModel
        binding.modify.setOnClickListener{
            //수정 api호출
        }
        binding.delete.setOnClickListener{
            //삭제 api호출
        }
    }


}