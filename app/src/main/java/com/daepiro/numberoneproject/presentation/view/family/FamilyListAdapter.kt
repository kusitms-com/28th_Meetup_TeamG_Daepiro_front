package com.daepiro.numberoneproject.presentation.view.family

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.daepiro.numberoneproject.R
import com.daepiro.numberoneproject.databinding.ItemFamilyListBinding
import com.daepiro.numberoneproject.presentation.view.funding.main.FundingListAdapter

class FamilyListAdapter: RecyclerView.Adapter<FamilyListAdapter.CustomViewHolder>() {
    private lateinit var itemClickListener: OnItemClickListener
    private var familyList =  emptyList<String>()
    private var isManageMode = false

    inner class CustomViewHolder(private val binding: ItemFamilyListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            if (isManageMode) {
                binding.ivManage.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.ic_delete_orange))
            } else {
                binding.ivManage.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.ic_send_orange))
            }

            binding.ivManage.setOnClickListener {
                itemClickListener.onClickManage(it, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = ItemFamilyListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind()

        holder.itemView.setOnClickListener {
            itemClickListener.onClickItem(it, position)
        }
    }

    fun setData(newData: List<String>) {
        familyList = newData
        notifyDataSetChanged()
    }

    fun changeManageMode() {
        isManageMode = !isManageMode
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onClickItem(v: View, position: Int)
        fun onClickManage(v: View, position: Int)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    override fun getItemCount() = 7
}