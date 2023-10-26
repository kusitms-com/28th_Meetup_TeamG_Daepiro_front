package com.example.numberoneproject.presentation.view.networkerror

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    }

    override fun setupInit() {
        super.setupInit()
        binding.actionBtn.setOnClickListener{
            //fragment전체 화면
        }
        binding.shelterBtn.setOnClickListener{
            //또다른 dialog
        }
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

}