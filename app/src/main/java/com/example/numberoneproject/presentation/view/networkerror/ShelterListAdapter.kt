package com.example.numberoneproject.presentation.view.networkerror

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.numberoneproject.R
import com.example.numberoneproject.data.model.ShelterData
import com.example.numberoneproject.data.model.ShelterRecyclerList

class ShelterListAdapter : RecyclerView.Adapter<ShelterListAdapter.ViewHolder>() {
    var shelters : List<ShelterRecyclerList> = emptyList()
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val fullAddress : TextView = itemView.findViewById(R.id.fullAddress)
        private val facilityFullName : TextView = itemView.findViewById(R.id.fullName)
        fun bind(shelterData : ShelterRecyclerList){
            fullAddress.text = shelterData.fullAddress
            facilityFullName.text = shelterData.facilityFullName
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShelterListAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.listitem_shelter,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShelterListAdapter.ViewHolder, position: Int) {
        holder.bind(shelters[position])
    }

    override fun getItemCount(): Int {
        return shelters.size
    }

    fun updateShelters(newShelters:List<ShelterRecyclerList>){
        Log.d("checkshelteradapter", "updateShelters called with new data size: ${newShelters.size}")
        shelters = newShelters
        notifyDataSetChanged()
    }

}