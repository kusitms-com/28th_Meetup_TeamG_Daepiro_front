package com.daepiro.numberoneproject.presentation.view.networkerror

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.daepiro.numberoneproject.R
import com.daepiro.numberoneproject.data.model.DisastertypeDataModel
import com.daepiro.numberoneproject.databinding.FragmentBehaviorTabBBinding
import com.daepiro.numberoneproject.presentation.base.BaseFragment
import com.daepiro.numberoneproject.presentation.view.login.onboarding.GridviewAdapter

class BehaviorTabBFragment : BaseFragment<FragmentBehaviorTabBBinding>(R.layout.fragment_behavior_tab_b) {
    private lateinit var adapter: GridviewAdapter
    companion object{
        private var instance: BehaviorTabBFragment? = null
        fun newInstance(): BehaviorTabBFragment{
            if(instance == null){
                instance = BehaviorTabBFragment()
            }
            return instance!!
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data = setData()
        binding.recycler.layoutManager = GridLayoutManager(requireContext(),3)
        adapter = GridviewAdapter(emptyList(),
            onItemClickListener = { disasterType, isSelected ->

            },
            onSelectionChanged = { isSelected ->
            },
            handleItemClick = { disasterType, isSelected ->
            }
        )
        binding.recycler.adapter = adapter
        adapter.updateList(data)
    }


    private fun setData():List<DisastertypeDataModel> = listOf(
        DisastertypeDataModel("사회재난","가스",R.drawable.ic_gas),
        DisastertypeDataModel("사회재난","교통",R.drawable.ic_traffic),
        DisastertypeDataModel("사회재난","금융",R.drawable.ic_money),
        DisastertypeDataModel("사회재난","붕괴",R.drawable.ic_collide),
        DisastertypeDataModel("사회재난","수도",R.drawable.ic_water),
        DisastertypeDataModel("사회재난","에너지",R.drawable.ic_energy),
        DisastertypeDataModel("사회재난","의료",R.drawable.ic_medical),
        DisastertypeDataModel("사회재난","전염병",R.drawable.ic_pendemic),
        DisastertypeDataModel("사회재난","정전",R.drawable.ic_lightoff),
        DisastertypeDataModel("사회재난","통신",R.drawable.ic_network),
        DisastertypeDataModel("사회재난","폭발",R.drawable.ic_explosion),
        DisastertypeDataModel("사회재난","화재",R.drawable.ic_fire),
        DisastertypeDataModel("사회재난","환경오염사고",R.drawable.ic_environment),
        DisastertypeDataModel("사회재난","AI",R.drawable.ic_ai),

        )

}