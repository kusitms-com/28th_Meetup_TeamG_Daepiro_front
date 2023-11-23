package com.daepiro.numberoneproject.presentation.view.community

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.daepiro.numberoneproject.R
import com.daepiro.numberoneproject.databinding.FragmentCommunityBinding
import com.daepiro.numberoneproject.presentation.base.BaseFragment
import com.daepiro.numberoneproject.presentation.util.Extensions.repeatOnStarted
import com.daepiro.numberoneproject.presentation.viewmodel.CommunityViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class CommunityFragment : BaseFragment<FragmentCommunityBinding>(R.layout.fragment_community) {
    val viewModel by activityViewModels<CommunityViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tabLayout = binding.tablayout
        val fragmentContainer = binding.fragmentContainer

        tabLayout.addOnTabSelectedListener(object :OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position){
                    0 -> {
                        showFragment(CommunityTabAFragment())
                        binding.forTaba.root.visibility = View.VISIBLE
                        binding.forTabb.visibility = View.GONE
                    }
                    1 -> {
                        showFragment(CommunityTabBFragment())
                        binding.forTabb.visibility = View.VISIBLE
                        binding.forTaba.root.visibility = View.GONE
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
        showFragment(CommunityTabAFragment())
        tabLayout.getTabAt(0)?.select()

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val selectedItem = p0?.getItemAtPosition(p2).toString()
                viewModel._selectRegion.value = selectedItem
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }
    }

    override fun setupInit() {
        super.setupInit()
        setSpinner(emptyList())
    }

    override fun subscribeUi() {
        super.subscribeUi()

        repeatOnStarted {
            viewModel.townList.collectLatest {townData->
                setSpinner(townData.regions)
            }
        }
    }

    private fun setSpinner(townData:List<String>){
        val adapter = ArrayAdapter(requireContext(), R.layout.item_spinner,townData)
        adapter.setDropDownViewResource(R.layout.item_spinner,)
        binding.spinner.adapter = adapter
        binding.spinner.post {
            binding.spinner.dropDownVerticalOffset = binding.spinner.height
        }
    }



    private fun showFragment(fragment:Fragment){
        childFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

}