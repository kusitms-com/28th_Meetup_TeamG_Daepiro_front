package com.example.numberoneproject.presentation.view.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.numberoneproject.R
import com.example.numberoneproject.data.model.Shelter
import com.example.numberoneproject.databinding.ItemAroundShelterAllBinding

class AroundShelterAllAdapter: RecyclerView.Adapter<AroundShelterAllAdapter.CustomViewHolder>() {
    private lateinit var itemClickListener: OnItemClickListener
    private var shelterList = listOf<Shelter>()
    private var isExpandedList = mutableListOf<Boolean>()

    inner class CustomViewHolder(val binding: ItemAroundShelterAllBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Shelter) {
            binding.model = item
            binding.tvAddress.text = item.changeLatLogToAddress(itemView.context)

            if (isExpandedList[position]) {
                binding.ivSearchLoad.setImageDrawable(itemView.context.getDrawable(R.drawable.ic_arrow_top))
                binding.llTopMap.visibility = View.VISIBLE
            } else {
                binding.ivSearchLoad.setImageDrawable(itemView.context.getDrawable(R.drawable.ic_arrow_down))
                binding.llTopMap.visibility = View.GONE
            }

            binding.btnNaverMap.setOnClickListener {
                itemClickListener.onClickNaverMap(it, adapterPosition)
            }
            binding.btnKakaoMap.setOnClickListener {
                itemClickListener.onClickKakaoMap(it, adapterPosition)
            }
            binding.btnTMap.setOnClickListener {
                itemClickListener.onClickTMap(it, adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AroundShelterAllAdapter.CustomViewHolder {
        val view = ItemAroundShelterAllBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: AroundShelterAllAdapter.CustomViewHolder, position: Int) {
        holder.bind(shelterList[position])

        holder.binding.llSearchLoad.setOnClickListener {
            isExpandedList[position] = !isExpandedList[position]
            notifyItemChanged(position)

        }
    }

    fun setData(newData: List<Shelter>) {
        shelterList = newData
        isExpandedList = MutableList(newData.size) { false }
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onClickNaverMap(v: View, position: Int)
        fun onClickKakaoMap(v: View, position: Int)
        fun onClickTMap(v: View, position: Int)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    override fun getItemCount() = shelterList.size
}