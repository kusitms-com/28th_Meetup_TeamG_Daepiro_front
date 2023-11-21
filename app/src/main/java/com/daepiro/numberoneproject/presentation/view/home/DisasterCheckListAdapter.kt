package com.daepiro.numberoneproject.presentation.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.daepiro.numberoneproject.databinding.ItemDisasterCheckListBinding

class DisasterCheckListAdapter: RecyclerView.Adapter<DisasterCheckListAdapter.CustomViewHolder>() {
    private var checkList = listOf<String>()
    private var resetCheckBox = false

    inner class CustomViewHolder(val binding: ItemDisasterCheckListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.tvCheckList.text = item

            binding.cbCheckList.isChecked = !resetCheckBox
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DisasterCheckListAdapter.CustomViewHolder {
        val view = ItemDisasterCheckListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: DisasterCheckListAdapter.CustomViewHolder, position: Int) {
        holder.bind(checkList[position])

    }

    fun setData(newData: List<String>) {
        checkList = newData
        notifyDataSetChanged()
    }

    fun resetCheckBox(boolean: Boolean) {
        resetCheckBox = boolean
    }

    override fun getItemCount() = checkList.size
}