package com.daepiro.numberoneproject.presentation.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.daepiro.numberoneproject.databinding.ItemAlarmDisasterBinding

class AlarmDisasterAdapter: RecyclerView.Adapter<AlarmDisasterAdapter.CustomViewHolder>() {
    private var alarmList = listOf<String>()

    inner class CustomViewHolder(val binding: ItemAlarmDisasterBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind() {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmDisasterAdapter.CustomViewHolder {
        val view = ItemAlarmDisasterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlarmDisasterAdapter.CustomViewHolder, position: Int) {
        holder.bind()
    }

    fun setData(newData: List<String>) {
        alarmList = newData
        notifyDataSetChanged()
    }

    override fun getItemCount() = 4
}
