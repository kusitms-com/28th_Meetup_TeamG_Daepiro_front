package com.daepiro.numberoneproject.presentation.view.login.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.daepiro.numberoneproject.R
import com.daepiro.numberoneproject.databinding.FragmentGuideLastBinding
import com.daepiro.numberoneproject.presentation.base.BaseFragment
import com.daepiro.numberoneproject.presentation.view.MainActivity

class GuideLastFragment : BaseFragment<FragmentGuideLastBinding>(R.layout.fragment_guide_last) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.startBtn.setOnClickListener{
            val intent = Intent(requireContext(),MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

}