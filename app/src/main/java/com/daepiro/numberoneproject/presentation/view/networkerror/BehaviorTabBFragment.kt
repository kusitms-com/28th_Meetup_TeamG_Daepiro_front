package com.daepiro.numberoneproject.presentation.view.networkerror

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.daepiro.numberoneproject.R

class BehaviorTabBFragment : Fragment() {
    companion object{
        private var instance: BehaviorTabBFragment? = null
        fun newInstance(): BehaviorTabBFragment{
            if(instance == null){
                instance = BehaviorTabBFragment()
            }
            return instance!!
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_behavior_tab_b, container, false)
    }

}