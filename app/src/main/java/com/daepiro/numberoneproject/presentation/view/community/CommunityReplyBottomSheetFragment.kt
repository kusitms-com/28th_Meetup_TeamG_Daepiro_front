package com.daepiro.numberoneproject.presentation.view.community

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.daepiro.numberoneproject.R

import com.daepiro.numberoneproject.databinding.FragmentCommunityReplydeleteBottomSheetBinding
import com.daepiro.numberoneproject.presentation.viewmodel.CommunityViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CommunityReplyBottomSheetFragment : BottomSheetDialogFragment() {
    private val viewModel: CommunityViewModel by activityViewModels()
    private var _binding:FragmentCommunityReplydeleteBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCommunityReplydeleteBottomSheetBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.modify.setOnClickListener{
            //수정 api호출
        }
        binding.delete.setOnClickListener{
            //삭제 api호출
            viewModel.updateAdditionalType(binding.delete.text.toString())
            //탭
            findNavController().popBackStack()
        }
    }


}