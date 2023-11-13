package com.daepiro.numberoneproject.presentation.view.networkerror

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.daepiro.numberoneproject.R

class BehaviorTabCFragment : Fragment() {
    companion object{
        private var instance: BehaviorTabCFragment? = null
        fun newInstance(): BehaviorTabCFragment{
            if(instance == null){
                instance = BehaviorTabCFragment()
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
        return inflater.inflate(R.layout.fragment_behavior_tab_c, container, false)
    }

}