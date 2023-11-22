package com.daepiro.numberoneproject.presentation.view.community

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.daepiro.numberoneproject.R
import com.daepiro.numberoneproject.databinding.FragmentTagSelectBottomBinding
import com.daepiro.numberoneproject.presentation.viewmodel.CommunityViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TagSelectBottomFragment : BottomSheetDialogFragment() {
    private val vieWModel:CommunityViewModel by activityViewModels()
    private var _binding:FragmentTagSelectBottomBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTagSelectBottomBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = vieWModel

        binding.closeBtn.setOnClickListener{
            dismiss()
        }

        binding.tag1.setOnClickListener{
            vieWModel._tagData.postValue(binding.tag1.text.toString())
        }
        binding.tag2.setOnClickListener{
            vieWModel._tagData.postValue(binding.tag2.text.toString())
        }
        binding.tag3.setOnClickListener{
            vieWModel._tagData.postValue(binding.tag3.text.toString())
        }
        binding.tag4.setOnClickListener{
            vieWModel._tagData.postValue(binding.tag3.text.toString())
        }
        binding.complete.setOnClickListener{
            dismiss()
        }


    }

}