package com.example.numberoneproject.presentation.view.funding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.numberoneproject.databinding.ItemFundingListBinding

class FundingListAdapter: RecyclerView.Adapter<FundingListAdapter.CustomViewHolder>() {
    private lateinit var itemClickListener: OnItemClickListener

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

        holder.itemView.setOnClickListener {
            itemClickListener.onClickItem(it, position)
        }
    }

    interface OnItemClickListener {
        fun onClickItem(v: View, position: Int)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    override fun getItemCount() = 7
}