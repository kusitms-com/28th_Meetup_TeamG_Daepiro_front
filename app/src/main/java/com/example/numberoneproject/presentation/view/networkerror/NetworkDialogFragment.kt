package com.example.numberoneproject.presentation.view.networkerror

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.example.numberoneproject.R
import com.example.numberoneproject.databinding.FragmentNetworkDialogBinding
import com.example.numberoneproject.presentation.base.BaseDialogFragment

class NetworkDialogFragment : BaseDialogFragment<FragmentNetworkDialogBinding>(R.layout.fragment_network_dialog) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupInit()
    }

    override fun setupInit() {
        super.setupInit()
        binding.actionBtn.setOnClickListener{
            //fragment전체 화면
        }
        binding.shelterBtn.setOnClickListener{
            //또다른 dialog
        }
        setTextColor(binding.errorTxt, binding.errorTxt.text.toString(), listOf("행동요령", "대피소 조회"))
    }

    override fun onStart() {
        //다이얼로그 가운데 띄워지도록
        super.onStart()
        val window = dialog?.window ?: return
        val params = window.attributes

        params.width = (resources.displayMetrics.widthPixels * 0.84).toInt()
        window.attributes = params

        window.setGravity(Gravity.CENTER)
    }

    private fun setTextColor(textView: TextView, fullText:String, wordsToColor:List<String>){
        val color = ContextCompat.getColor(textView.context, R.color.orange_500)
        val spannableStringBuilder = SpannableStringBuilder(fullText)
        for(word in wordsToColor){
            var startIndex = fullText.indexOf(word)
            while(startIndex != -1){
                val endIndex = startIndex + word.length
                spannableStringBuilder.setSpan(ForegroundColorSpan(color),
                    startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                startIndex = fullText.indexOf(word, startIndex+1)
            }
        }
        textView.text = spannableStringBuilder
    }

}