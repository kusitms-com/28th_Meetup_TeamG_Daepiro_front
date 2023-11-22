package com.daepiro.numberoneproject.presentation.view.login.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.daepiro.numberoneproject.R
import com.daepiro.numberoneproject.databinding.FragmentGuideFirstBinding
import com.daepiro.numberoneproject.presentation.base.BaseFragment
import com.daepiro.numberoneproject.presentation.view.login.LoginFragmentDirections

class GuideFirstFragment: BaseFragment<FragmentGuideFirstBinding>(R.layout.fragment_guide_first) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_guideFirstFragment_to_loginFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this,callback)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        binding.btnNext.setOnClickListener {
            val action = GuideFirstFragmentDirections.actionGuideFirstFragmentToGuideSecondFragment()
            findNavController().navigate(action)
        }
    }
}