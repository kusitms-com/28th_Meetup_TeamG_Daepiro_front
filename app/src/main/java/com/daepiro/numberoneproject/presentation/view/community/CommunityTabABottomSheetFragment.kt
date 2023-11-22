package com.daepiro.numberoneproject.presentation.view.community

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import com.daepiro.numberoneproject.R
import com.daepiro.numberoneproject.data.model.ConversationRequestBody
import com.daepiro.numberoneproject.databinding.FragmentCommunityTabABottomSheetBinding
import com.daepiro.numberoneproject.presentation.util.Extensions.repeatOnStarted
import com.daepiro.numberoneproject.presentation.viewmodel.CommunityViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.flow.collectLatest

//댓글 더보기 화면
class CommunityTabABottomSheetFragment : BottomSheetDialogFragment() {
    private val viewModel: CommunityViewModel by activityViewModels()
    private var _binding:FragmentCommunityTabABottomSheetBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter:CommunityTabABottomSheetAdapter
    var content:String= ""

    interface CommentPostListener{
        fun onCommentPasted()
    }
    var commentPostListener:CommentPostListener? = null


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

        repeatOnStarted {
            viewModel.disasterHomeDetail.collectLatest {detail->
                Log.d("repeat", "${detail.conversations}")
                adapter.updateList(detail.conversations)
            }
        }

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val selectedItem = p0?.getItemAtPosition(p2).toString()
                if(selectedItem == "인기순"){
                    viewModel.getDisasterDetail("popularity", viewModel.id)
                }else{
                    viewModel.getDisasterDetail("time", viewModel.id)
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }




        binding.editBox.addTextChangedListener( object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                content = p0.toString()
            }

        })

        binding.sendBtn.setOnClickListener{
            postReply(content,viewModel.id)
            binding.editBox.text.clear()
        }


        binding.closeBtn.setOnClickListener{
            dismiss()
        }
    }



    private fun setupRecyclerview(){
        adapter = CommunityTabABottomSheetAdapter(emptyList(), object : CommunityTabABottomSheetAdapter.onItemClickListener{
            override fun onItemClickListener() {
            }

        })
        binding.recyclerview.adapter = adapter
    }

    private fun setSpinner(){
        val spinnerData = listOf("최신순", "인기순")
        val adapter = ArrayAdapter(requireContext(), R.layout.item_spinner,spinnerData)
        adapter.setDropDownViewResource(R.layout.item_spinner,)
        binding.spinner.adapter = adapter
    }


    override fun onStart() {
        super.onStart()
        val dialog = dialog as? BottomSheetDialog
        val bottomSheet = dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        bottomSheet?.layoutParams?.height = ViewGroup.LayoutParams.MATCH_PARENT

        // BottomSheet의 상태를 확장 상태로 설정
        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet!!)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetBehavior.peekHeight = 0
        bottomSheetBehavior.isDraggable = false
    }

    private fun postReply(content:String,disasterId:Int){
        val body = ConversationRequestBody(
            disasterId = disasterId,
            content = content
        )
        viewModel.postDisasterConversation(body).apply {
            commentPostListener?.onCommentPasted()
        }
    }

}