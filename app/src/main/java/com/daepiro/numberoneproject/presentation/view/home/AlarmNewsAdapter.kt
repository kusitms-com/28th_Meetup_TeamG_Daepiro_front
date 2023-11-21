package com.daepiro.numberoneproject.presentation.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.daepiro.numberoneproject.databinding.ItemAlarmDisasterBinding
import com.daepiro.numberoneproject.databinding.ItemAlarmNewsBinding

class AlarmNewsAdapter: RecyclerView.Adapter<AlarmNewsAdapter.CustomViewHolder>() {
    private var alarmList = listOf<String>()

    inner class CustomViewHolder(val binding: ItemAlarmNewsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind() {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmNewsAdapter.CustomViewHolder {
        val view = ItemAlarmNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlarmNewsAdapter.CustomViewHolder, position: Int) {
        holder.bind()
    }

    fun setData(newData: List<String>) {
        alarmList = newData
        notifyDataSetChanged()
    }

    override fun getItemCount() = 7
}
