package com.daepiro.numberoneproject.presentation.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.daepiro.numberoneproject.data.model.Contents
import com.daepiro.numberoneproject.databinding.ItemAlarmDisasterBinding
import com.daepiro.numberoneproject.databinding.ItemAlarmNewsBinding

class AlarmNewsAdapter: RecyclerView.Adapter<AlarmNewsAdapter.CustomViewHolder>() {
    private var alarmList = listOf<Contents>()

    inner class CustomViewHolder(val binding: ItemAlarmNewsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Contents) {
            binding.model = item

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmNewsAdapter.CustomViewHolder {
        val view = ItemAlarmNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlarmNewsAdapter.CustomViewHolder, position: Int) {
        holder.bind(alarmList[position])
    }

    fun setData(newData: List<Contents>) {
        alarmList = newData
        notifyDataSetChanged()
    }

    override fun getItemCount() = alarmList.size
}
