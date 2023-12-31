package com.daepiro.numberoneproject.presentation.view.networkerror

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.daepiro.numberoneproject.R

class LocationAdapter(private var items: List<String>): RecyclerView.Adapter<LocationAdapter.ViewHolder>() {
    var itemClickListener: ((position:Int, value:String) -> Unit)? = null
    private var selectedPosition:Int = -1
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val location : TextView = itemView.findViewById(R.id.location_item)
        init{
            itemView.setOnClickListener{
                val position = adapterPosition
                if(position != RecyclerView.NO_POSITION){
                    selectItem(position)
                    itemClickListener?.invoke(position, items[position])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_location,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val list = items[position]
        holder.location.text = list

        holder.itemView.setBackgroundResource(
            if(position == selectedPosition) R.drawable.bg_location_select
            else R.drawable.bg_location_normal
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }
    fun updateList(newData:List<String>){
        this.items = newData
        //이전 선택 상태 초기화
        val previousSelectedPosition = selectedPosition
        selectedPosition = -1
        notifyDataSetChanged()
        //이전 선택 위치 유효 확인후 유효할시 다시 선택상태 만듬
        if(previousSelectedPosition >=0 && previousSelectedPosition < items.size){
            selectItem(previousSelectedPosition)
        }
    }
    fun selectItem(position:Int){
        val previousSelectedPosition = selectedPosition
        if(selectedPosition != position){
            selectedPosition = position
            notifyItemChanged(previousSelectedPosition)
            notifyItemChanged(position)
        }
    }

}