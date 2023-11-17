package com.daepiro.numberoneproject.presentation.view.community

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import org.w3c.dom.Text

@AndroidEntryPoint
class CommunityTabBFragment : BaseFragment<FragmentCommunityTabBBinding>(R.layout.fragment_community_tab_b) {
    val viewModel by activityViewModels<CommunityViewModel>()
    private lateinit var adapter : TownCommentListAdapter
    private var lastItemId:Int? = null
    private var isLoading = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        binding.viewModel = viewModel
        binding.all.isSelected = true


        viewModel.getTownCommentList(10,null,null)
        collectTownCommentList()
        setInfiniteScroll(null)

        //카테고리 tag들
        val tags = listOf(binding.all, binding.traffic, binding.safety, binding.life)
        tags.forEach{textview->
            textview.setOnClickListener{
                selectTags(textview,tags)
                Log.d("tags","${textview.isSelected}")
            }
        }

        binding.writeBtn.setOnClickListener{
            findNavController().navigate(R.id.communityTownWritingFragment)
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
                viewModel.getTownDetail(id)
                findNavController().navigate(R.id.action_communityFragment_to_communityTownDetailFragment)
            }
        })
        binding.recycler.adapter = adapter
    }

    private fun setInfiniteScroll(tag:String?) {
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
                    viewModel.getTownCommentList(10, tag, lastItemId) // lastItemId를 API 호출에 포함
                }
            }
        })
    }

    private fun clearUpdateData(tag:String?){
        adapter.clearData()
        viewModel.getTownCommentList(10,tag,null)
        collectTownCommentList()
        setInfiniteScroll(tag)
    }

    private fun selectTags(selectedTag: TextView, textviews:List<TextView>){
        textviews.forEach{
            it.isSelected = textviews == selectedTag
        }
        when(selectedTag){
            binding.all ->{
                binding.all.isSelected = !binding.all.isSelected
                clearUpdateData(null)

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
        }
    }



}