package com.example.numberoneproject.presentation.view.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.numberoneproject.data.model.Shelter
import com.example.numberoneproject.databinding.ItemAroundShelterBinding
import com.example.numberoneproject.presentation.view.funding.FundingCategoryAdapter

class AroundShelterAdapter: RecyclerView.Adapter<AroundShelterAdapter.CustomViewHolder>() {
    private lateinit var itemClickListener: OnItemClickListener
    private var data = listOf<Shelter>()

    inner class CustomViewHolder(val binding: ItemAroundShelterBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Shelter) {
            binding.tvTitle.text = item.facilityName
            binding.tvAddress.text = item.changeLatLogToAddress(itemView.context)
            binding.tvDistance.text = item.distance.split(".")[0] + "m"


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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AroundShelterAdapter.CustomViewHolder {
        val view = ItemAroundShelterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: AroundShelterAdapter.CustomViewHolder, position: Int) {
        holder.bind(data[position])
    }

    fun setData(newData: List<Shelter>) {
        data = newData
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

    override fun getItemCount() = data.size
}