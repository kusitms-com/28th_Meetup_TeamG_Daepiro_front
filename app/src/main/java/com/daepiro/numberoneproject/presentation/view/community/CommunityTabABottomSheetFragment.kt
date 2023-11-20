package com.daepiro.numberoneproject.presentation.view.community

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import com.daepiro.numberoneproject.R
import com.daepiro.numberoneproject.databinding.FragmentCommunityTabABinding
import com.daepiro.numberoneproject.databinding.FragmentCommunityTabABottomSheetBinding
import com.daepiro.numberoneproject.presentation.viewmodel.CommunityViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
//댓글 더보기 화면
class CommunityTabABottomSheetFragment : BottomSheetDialogFragment() {
    private val viewModel: CommunityViewModel by activityViewModels()
    private var _binding:FragmentCommunityTabABottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCommunityTabABottomSheetBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setSpinner()
        setupRecyclerview()

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val selectedItem = p0?.getItemAtPosition(p2).toString()
                //recycler updatelist 호출
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        //collect
    }




    private fun setupRecyclerview(){

    }

    private fun setSpinner(){
        val spinnerData = listOf("최신순", "인기순")
        val adapter = ArrayAdapter(requireContext(), R.layout.item_spinner,spinnerData)
        adapter.setDropDownViewResource(R.layout.item_spinner,)
        binding.spinner.adapter = adapter
    }






}