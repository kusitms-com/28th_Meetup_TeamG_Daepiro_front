package com.example.numberoneproject.presentation.view.networkerror

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.numberoneproject.R
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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_shelter,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShelterListAdapter.ViewHolder, position: Int) {
        val shelterData = shelters[position]
        holder.bind(shelterData)

        holder.itemView.setOnTouchListener{v,event->
            when(event.action){
                MotionEvent.ACTION_DOWN->{
                    v.isSelected = true
                }
                MotionEvent.ACTION_UP->{
                    v.isSelected = false
                }
            }
            return@setOnTouchListener true
        }

        holder.itemView.findViewById<TextView>(R.id.copy_btn).setOnClickListener{
            val clipboard = holder.itemView.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("shelter_address", shelterData.fullAddress)
            clipboard.setPrimaryClip(clip)

            //다이얼로그 표시
            val dialogFragment = CopyCompleteFragment()
            dialogFragment.show((holder.itemView.context as FragmentActivity).supportFragmentManager,"dialog")
        }
    }

    override fun getItemCount(): Int {
        return shelters.size
    }

    fun updateShelters(newShelters:List<ShelterRecyclerList>){
        shelters = newShelters
        notifyDataSetChanged()
    }

}