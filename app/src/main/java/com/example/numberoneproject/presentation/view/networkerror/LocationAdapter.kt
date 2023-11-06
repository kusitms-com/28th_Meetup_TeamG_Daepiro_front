package com.example.numberoneproject.presentation.view.networkerror

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.numberoneproject.R

class LocationAdapter(private var items: List<String>): RecyclerView.Adapter<LocationAdapter.ViewHolder>() {
    var itemClickListener: ((position:Int, value:String) -> Unit)? = null
    private var selectedPosition:Int = -1
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val location : TextView = itemView.findViewById(R.id.location_item)
        init{
            itemView.setOnClickListener{
                val position = adapterPosition
                if(position != RecyclerView.NO_POSITION){
                    if(selectedPosition != -1){
                        notifyItemChanged(selectedPosition)
                    }
                    selectedPosition = position
                    notifyItemChanged(selectedPosition)
                    itemClickListener?.invoke(position, items[position])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.listitem_location,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val list = items[position]
        holder.location.text = list
        holder.itemView.isSelected = (selectedPosition == position)
    }

    override fun getItemCount(): Int {
        return items.size
    }
    fun updateList(newData:List<String>){
        this.items = newData
        notifyDataSetChanged()
    }
    fun getSelectedText():String?{
        return if(selectedPosition != RecyclerView.NO_POSITION){
            items[selectedPosition]
        }
        else{
            null
        }
    }
}