package com.daepiro.numberoneproject.presentation.view.family

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.daepiro.numberoneproject.databinding.ItemCheerMessageBinding
import com.daepiro.numberoneproject.databinding.ItemFamilyListBinding

class FamilyListAdapter: RecyclerView.Adapter<FamilyListAdapter.CustomViewHolder>() {
    private var familyList =  emptyList<String>()
    inner class CustomViewHolder(private val binding: ItemFamilyListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind() {
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = ItemFamilyListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind()
    }

    fun setData(newData: List<String>) {
        familyList = newData
        notifyDataSetChanged()
    }

    override fun getItemCount() = 7
}