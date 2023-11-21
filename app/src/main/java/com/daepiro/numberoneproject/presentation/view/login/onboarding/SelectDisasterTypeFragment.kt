package com.daepiro.numberoneproject.presentation.view.login.onboarding

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.daepiro.numberoneproject.R
import com.daepiro.numberoneproject.data.model.DisastertypeDataModel
import com.daepiro.numberoneproject.databinding.FragmentSelectDisasterTypeBinding
import com.daepiro.numberoneproject.presentation.base.BaseFragment
import com.daepiro.numberoneproject.presentation.viewmodel.OnboardingViewModel

class SelectDisasterTypeFragment : BaseFragment<FragmentSelectDisasterTypeBinding>(R.layout.fragment_select_disaster_type) {
    private val viewModel: OnboardingViewModel by activityViewModels()
    private lateinit var adapter:GridviewAdapter
    private val selectedItems = mutableListOf<DisastertypeDataModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        val data = setData()
        adapter.updateList(data)

        binding.check.setOnCheckedChangeListener{_, isChecked ->
            if (isChecked) {
                adapter.selectAllItems()
            } else {
                adapter.deselectAllItems()
            }
        }


        binding.allCategory.setOnClickListener{
            val data = setData()
            adapter.updateList(data)
        }
        binding.naturlDisaster.setOnClickListener{
            adapter.filterByCategory("자연재난")
        }
        binding.socialDisaster.setOnClickListener{
            adapter.filterByCategory("사회재난")
        }
        binding.emergency.setOnClickListener{
            adapter.filterByCategory("비상대비")
        }
        binding.etc.setOnClickListener{
            adapter.filterByCategory("기타")
        }

        binding.completeBtn.setOnClickListener{
            Log.d("completeBtn","$selectedItems")
        }
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

    }

    override fun subscribeUi() {
        super.subscribeUi()
    }

    override fun setupInit() {
        super.setupInit()
        setupRecycler()
    }

    private fun setupRecycler(){
        binding.recyclerview.layoutManager = GridLayoutManager(requireContext(),3)
        adapter = GridviewAdapter(emptyList(), object : GridviewAdapter.onItemClickListener{
            override fun onItemClickListener(disasterType: String, isSelected:Boolean) {
                handleItemClick(disasterType,isSelected)
            }

        })
        binding.recyclerview.adapter = adapter
    }

    private fun handleItemClick(disasterType: String, isSelected:Boolean){
        val item = adapter.getItemList().find{it.disasterType == disasterType}
        item?.let {
            it.isSelected = isSelected
            if (isSelected) {
                selectedItems.add(it)
            } else {
                selectedItems.remove(it)
            }
        }
    }

    private fun setData():List<DisastertypeDataModel> = listOf(
        DisastertypeDataModel("자연재난","가뭄",R.drawable.ic_drought),
        DisastertypeDataModel("자연재난","강풍",R.drawable.ic_wind),
        DisastertypeDataModel("사회재난","가스",R.drawable.ic_gas),
        DisastertypeDataModel("자연재난","건조",R.drawable.ic_dry),
        DisastertypeDataModel("사회재난","교통",R.drawable.ic_traffic),
        DisastertypeDataModel("사회재난","금융",R.drawable.ic_money),
        DisastertypeDataModel("자연재난","대설",R.drawable.ic_snow),
        DisastertypeDataModel("자연재난","대조기",R.drawable.ic_daejo),
        DisastertypeDataModel("자연재난","미세먼지",R.drawable.ic_dust),
        DisastertypeDataModel("사회재난","붕괴",R.drawable.ic_collide),
        DisastertypeDataModel("비상대비","비상사태",R.drawable.ic_emergency),
        DisastertypeDataModel("자연재난","산불",R.drawable.ic_monutinefire),
        DisastertypeDataModel("자연재난","산사태",R.drawable.ic_landslide),
        DisastertypeDataModel("사회재난","수도",R.drawable.ic_water),
        DisastertypeDataModel("기타","실종",R.drawable.ic_missing),
        DisastertypeDataModel("자연재난","안개",R.drawable.ic_fog),
        DisastertypeDataModel("사회재난","에너지",R.drawable.ic_energy),
        DisastertypeDataModel("사회재난","의료",R.drawable.ic_medical),
        DisastertypeDataModel("사회재난","전염병",R.drawable.ic_pendemic),
        DisastertypeDataModel("비상대비","정전",R.drawable.ic_lightoff),
        DisastertypeDataModel("자연재난","지진",R.drawable.ic_earthquake),

        DisastertypeDataModel("자연재난","태풍",R.drawable.ic_storm),
        DisastertypeDataModel("비상대비","테러",R.drawable.ic_terror),
        DisastertypeDataModel("사회재난","통신",R.drawable.ic_network),
        DisastertypeDataModel("사회재난","폭발",R.drawable.ic_explosion),
        DisastertypeDataModel("자연재난","폭염",R.drawable.ic_hot),
        DisastertypeDataModel("자연재난","풍랑",R.drawable.ic_wave),
        DisastertypeDataModel("자연재난","한파",R.drawable.ic_cold),
        DisastertypeDataModel("자연재난","호우",R.drawable.ic_rain),
        DisastertypeDataModel("자연재난","홍수",R.drawable.ic_flood),
        DisastertypeDataModel("비상대비","화생방사고",R.drawable.ic_cbr),
        DisastertypeDataModel("사회재난","화재",R.drawable.ic_fire),
        DisastertypeDataModel("사회재난","환경오염사고",R.drawable.ic_environment),
        DisastertypeDataModel("사회재난","AI",R.drawable.ic_ai),
        DisastertypeDataModel("기타","기타",R.drawable.ic_add),

        )




}