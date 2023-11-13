package com.daepiro.numberoneproject.presentation.view.networkerror

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.daepiro.numberoneproject.R
import com.daepiro.numberoneproject.databinding.FragmentNetworkDialogBinding
import com.daepiro.numberoneproject.presentation.base.BaseDialogFragment

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
            val intent = Intent(context, BehaviorTipActivity::class.java)
            startActivity(intent)
            dismiss()
        }
        binding.shelterBtn.setOnClickListener{
            val intent = Intent(context, CheckShelterActivity::class.java)
            startActivity(intent)
            dismiss()
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