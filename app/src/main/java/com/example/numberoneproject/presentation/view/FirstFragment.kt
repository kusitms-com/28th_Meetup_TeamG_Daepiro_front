package com.example.numberoneproject.presentation.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.numberoneproject.R
import com.example.numberoneproject.databinding.FragmentFirstBinding
import com.example.numberoneproject.presentation.base.BaseFragment
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FirstFragment : BaseFragment<FragmentFirstBinding>(R.layout.fragment_first) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvDefault.setOnClickListener {
            val action = FirstFragmentDirections.actionFirstFragmentToSubFragment(number = 100)
            findNavController().navigate(action)

        }
        binding.out.setOnClickListener{
            kakaoUnlink()
        }


    }

    override fun initView() {

    }

    private fun kakaoUnlink() {
        UserApiClient.instance.unlink { error->
            if(error != null){
                Log.e("unlink", "shut down: ${error}")
            }
            else{
                Log.e("unlink", "sdk에서 토큰 삭제")
                val intent = Intent(requireContext(),LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }


}