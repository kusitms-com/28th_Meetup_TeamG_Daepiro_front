package com.daepiro.numberoneproject.presentation.view.community

import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.Manifest
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.daepiro.numberoneproject.R
import com.daepiro.numberoneproject.databinding.FragmentCommunityTabBBinding
import com.daepiro.numberoneproject.presentation.base.BaseFragment
import com.daepiro.numberoneproject.presentation.util.Extensions.repeatOnStarted
import com.daepiro.numberoneproject.presentation.viewmodel.CommunityViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.w3c.dom.Text

@AndroidEntryPoint
class CommunityTabBFragment : BaseFragment<FragmentCommunityTabBBinding>(R.layout.fragment_community_tab_b) {
    val viewModel by activityViewModels<CommunityViewModel>()
    private lateinit var adapter : TownCommentListAdapter
    private var lastItemId:Int? = null
    private var isLoading = false
    private var region : String = ""
    private var latitude:Double=0.0
    private var longitude:Double=0.0
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        binding.viewModel = viewModel
        binding.all.isSelected = true

        //위경도를 가져옴과 동시에 초기 api호출
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        getCurrentLocation()


        //카테고리 tag들
        val tags = listOf(binding.all, binding.life, binding.traffic, binding.safety,binding.other)
        tags.forEach{textview->
            textview.setOnClickListener{
                selectTags(textview,tags)
            }
        }

        binding.writeBtn.setOnClickListener{
            findNavController().navigate(R.id.communityTownWritingFragment)
        }

    }

    override fun setupInit() {
        super.setupInit()
    }

    override fun subscribeUi() {
        super.subscribeUi()
        collectTownCommentList()

        repeatOnStarted {
            viewModel.selectRegion.collectLatest { response->
                region = response
            }
        }

    }

    private fun getCurrentLocation(){
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // 권한 요청
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), 1)
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.let {
                    latitude = it.latitude
                    longitude = it.longitude
                    lifecycleScope.launch {
                        if(isAdded){
                            viewModel.selectRegion.collect{region->
                                viewModel.getTownCommentList(10,"",null,longitude,latitude,viewModel.selectRegion.value)
                                setInfiniteScroll("")
                            }
                        }
                    }
                }
            }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
           1 -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    getCurrentLocation()
                } else {
                    // 권한 거부 처리
                }
            }
        }
    }
    private fun collectTownCommentList(){
        repeatOnStarted {
            viewModel.townCommentList.collect {response->
                if(response.empty){
                    isLoading = false
                    return@collect
                }
                adapter.updateList(response.content)
                lastItemId = response.content.lastOrNull()?.id
                isLoading = false
                Log.d("collectTownCommentList","${response.content}")
            }
        }
    }
    private fun setUpRecyclerView(){
        adapter = TownCommentListAdapter(emptyList(),object :TownCommentListAdapter.onItemClickListener{
            override fun onItemClick(id: Int) {
                Log.d("taaaaag","$id")
                //viewModel.id = id
                viewModel.getTownDetail(id)
                viewModel.setReply(id)
                findNavController().navigate(R.id.action_communityFragment_to_communityTownDetailFragment)
            }
        })
        binding.recycler.adapter = adapter
    }

    private fun setInfiniteScroll(tag:String) {
        binding.recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                // 이미 로딩 중이거나 위로 스크롤하는 경우 무시
                if (isLoading || dy <= 0) return

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

                // 목록의 마지막 아이템에 도달했을 때 API 호출
                if (totalItemCount - 1 == lastVisibleItemPosition) {
                    isLoading = true
                    viewModel.getTownCommentList(10, tag, lastItemId,longitude,latitude,region) // lastItemId를 API 호출에 포함
                }
            }
        })
    }

    //태그를 누를때 데이터 셋팅하는 함수
    private fun clearUpdateData(tag:String){
//        adapter.clearData()
//        lifecycleScope.launch {
//            viewModel.selectRegion.collect{region->
//                viewModel.getTownCommentList(10,tag,null,longitude,latitude,region)
//            }
//        }
//        collectTownCommentList()
//        setInfiniteScroll(tag)
        if(!isLoading){
            isLoading = true
            adapter.clearData()
            lifecycleScope.launch {
                viewModel.selectRegion.collect{region->
                    viewModel.getTownCommentList(10,tag,null,longitude,latitude,region)
                }
            }
        }
    }


    private fun selectTags(selectedTag: TextView, textviews:List<TextView>){
        textviews.forEach{
            it.isSelected = textviews == selectedTag
        }
        when(selectedTag){
            binding.all ->{
                binding.all.isSelected = !binding.all.isSelected
                clearUpdateData("")

            }
            binding.life -> {
                binding.life.isSelected = !binding.life.isSelected
                clearUpdateData("LIFE")
            }
            binding.safety -> {
                binding.safety.isSelected = !binding.safety.isSelected
                clearUpdateData("SAFETY")
            }
            binding.traffic -> {
                binding.traffic.isSelected = !binding.traffic.isSelected
                clearUpdateData("TRAFFIC")
            }
            else ->{
                binding.other.isSelected = !binding.other.isSelected
                clearUpdateData("NONE")
            }
        }
    }


}