package com.daepiro.numberoneproject.presentation.view.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.daepiro.numberoneproject.data.model.Contents
import com.daepiro.numberoneproject.databinding.ItemAlarmDisasterBinding

class AlarmDisasterAdapter: RecyclerView.Adapter<AlarmDisasterAdapter.CustomViewHolder>() {
    private var alarmList = listOf<Contents>()

    inner class CustomViewHolder(val binding: ItemAlarmDisasterBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Contents) {
            binding.model = item

            val dateArr = item.createdAt.replace("-", " ").split(" ")
            binding.tvLocationAndDate.text = "${item.location} · ${dateArr[0]}년 ${dateArr[1]}월 ${dateArr[2]}일 ${dateArr[3]}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmDisasterAdapter.CustomViewHolder {
        val view = ItemAlarmDisasterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlarmDisasterAdapter.CustomViewHolder, position: Int) {
        holder.bind(alarmList[position])
    }

    fun setData(newData: List<Contents>) {
        alarmList = newData
        notifyDataSetChanged()
    }

    override fun getItemCount() = alarmList.size
}
