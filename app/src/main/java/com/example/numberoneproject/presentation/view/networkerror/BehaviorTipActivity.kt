package com.example.numberoneproject.presentation.view.networkerror

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import com.example.numberoneproject.R
import com.example.numberoneproject.databinding.ActivityBehaviorTipBinding
import com.example.numberoneproject.presentation.base.BaseActivity
import com.google.android.material.tabs.TabLayout

class BehaviorTipActivity : BaseActivity<ActivityBehaviorTipBinding>(R.layout.activity_behavior_tip) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tabLayout = binding.tabLayout
        val fragmentManager = supportFragmentManager

        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, BehaviorTabAFragment())
        transaction.commit()

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val fragmentTransaction = fragmentManager.beginTransaction()
                when(tab?.position){
                    0 -> fragmentTransaction.replace(R.id.fragmentContainer, BehaviorTabAFragment.newInstance())
                    1 -> fragmentTransaction.replace(R.id.fragmentContainer, BehaviorTabBFragment.newInstance())
                    2 -> fragmentTransaction.replace(R.id.fragmentContainer, BehaviorTabCFragment.newInstance())
                }
                fragmentTransaction.commit()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

    }
}