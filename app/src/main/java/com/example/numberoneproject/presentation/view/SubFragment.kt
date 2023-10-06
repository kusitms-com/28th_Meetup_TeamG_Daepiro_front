package com.example.numberoneproject.presentation.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.navArgs
import com.example.numberoneproject.R
import com.example.numberoneproject.databinding.FragmentSubBinding
import com.example.numberoneproject.presentation.base.BaseFragment

class SubFragment: BaseFragment<FragmentSubBinding>(R.layout.fragment_sub) {
    private val args: SubFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("taag", args.toString())
    }

}