package com.daepiro.numberoneproject.presentation.view.funding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.daepiro.numberoneproject.R
import com.daepiro.numberoneproject.databinding.ItemFundingCategoryBinding

class FundingCategoryAdapter: RecyclerView.Adapter<FundingCategoryAdapter.CustomViewHolder>() {
    private lateinit var itemClickListener: OnItemClickListener
    private var selectedPosition = 0

    inner class CustomViewHolder(private val binding: ItemFundingCategoryBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            if (selectedPosition == adapterPosition) {
                binding.ivFundingCategory.setBackgroundResource(R.drawable.bg_funding_category_select_on)
                binding.tvFundingCategory.setTextColor(ContextCompat.getColor(itemView.context, R.color.orange_500))
            } else {
                binding.ivFundingCategory.setBackgroundResource(R.drawable.bg_funding_category_select_off)
                binding.tvFundingCategory.setTextColor(ContextCompat.getColor(itemView.context, R.color.secondary_600))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FundingCategoryAdapter.CustomViewHolder {
        val view = ItemFundingCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: FundingCategoryAdapter.CustomViewHolder, position: Int) {
        holder.bind()

        holder.itemView.setOnClickListener {
            selectedPosition = position
            itemClickListener.onClickItem(it, position, selectedPosition)

            notifyDataSetChanged()
        }
    }

    interface OnItemClickListener {
        fun onClickItem(v: View, position: Int, selectedPosition: Int)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    override fun getItemCount() = 10
}