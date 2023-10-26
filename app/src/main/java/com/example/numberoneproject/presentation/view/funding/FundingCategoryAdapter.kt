package com.example.numberoneproject.presentation.view.funding

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.numberoneproject.databinding.ItemFundingCategoryBinding

class FundingCategoryAdapter: RecyclerView.Adapter<FundingCategoryAdapter.CustomViewHolder>() {
    inner class CustomViewHolder(private val binding: ItemFundingCategoryBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Any) {

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FundingCategoryAdapter.CustomViewHolder {
        val view = ItemFundingCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: FundingCategoryAdapter.CustomViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount() = 10
}