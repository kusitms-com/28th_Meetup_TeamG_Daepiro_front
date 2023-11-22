package com.daepiro.numberoneproject.presentation.view.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.daepiro.numberoneproject.databinding.ItemDisasterCheckListBinding

class DisasterCheckListAdapter: RecyclerView.Adapter<DisasterCheckListAdapter.CustomViewHolder>() {
    private val checkStateList1 = MutableList(5) {false}
    private val checkStateList2 = MutableList(5) {false}
    private val checkStateList3 = MutableList(3) {false}
    private var selectedChip = 0

    private var checkList = listOf<String>()

    inner class CustomViewHolder(val binding: ItemDisasterCheckListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.tvCheckList.text = item

            binding.cbCheckList.setOnCheckedChangeListener(null)

            when(selectedChip) {
                1 -> {
                    binding.cbCheckList.isChecked = checkStateList1[position]
                    binding.cbCheckList.setOnCheckedChangeListener { compoundButton, b ->
                        checkStateList1[position] = b
                    }
                }
                2 -> {
                    binding.cbCheckList.isChecked = checkStateList2[position]
                    binding.cbCheckList.setOnCheckedChangeListener { compoundButton, b ->
                        checkStateList2[position] = b
                    }
                }
                3 -> {
                    binding.cbCheckList.isChecked = checkStateList3[position]
                    binding.cbCheckList.setOnCheckedChangeListener { compoundButton, b ->
                        checkStateList3[position] = b
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DisasterCheckListAdapter.CustomViewHolder {
        val view = ItemDisasterCheckListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: DisasterCheckListAdapter.CustomViewHolder, position: Int) {
        holder.bind(checkList[position])

    }

    fun setData(newData: List<String>, selectedPosition: Int) {
        checkList = newData
        selectedChip = selectedPosition
        notifyDataSetChanged()
    }

    override fun getItemCount() = checkList.size
}