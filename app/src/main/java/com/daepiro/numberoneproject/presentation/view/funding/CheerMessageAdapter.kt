package com.daepiro.numberoneproject.presentation.view.funding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.daepiro.numberoneproject.databinding.ItemCheerMessageBinding

class CheerMessageAdapter: RecyclerView.Adapter<CheerMessageAdapter.CustomViewHolder>() {
    private var messageList =  emptyList<String>()
    inner class CustomViewHolder(private val binding: ItemCheerMessageBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.tvMessage.text = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheerMessageAdapter.CustomViewHolder {
        val view = ItemCheerMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CheerMessageAdapter.CustomViewHolder, position: Int) {
        if (messageList.isNotEmpty()) {
            val adjustedPosition = position % messageList.size
            val item = messageList[adjustedPosition]
            holder.bind(item)
        }
    }

    fun setData(newData: List<String>) {
        messageList = newData
        notifyDataSetChanged()
    }

    override fun getItemCount() = Int.MAX_VALUE
}