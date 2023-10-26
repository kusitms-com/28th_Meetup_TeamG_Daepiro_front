package com.example.numberoneproject.presentation.view.funding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.numberoneproject.databinding.ItemFundingListBinding

class FundingListAdapter: RecyclerView.Adapter<FundingListAdapter.CustomViewHolder>() {
    inner class CustomViewHolder(private val binding: ItemFundingListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Any) {

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FundingListAdapter.CustomViewHolder {
        val view = ItemFundingListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: FundingListAdapter.CustomViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount() = 7
}