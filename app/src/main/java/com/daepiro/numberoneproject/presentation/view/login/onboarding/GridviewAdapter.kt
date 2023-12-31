package com.daepiro.numberoneproject.presentation.view.login.onboarding

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.daepiro.numberoneproject.R
import com.daepiro.numberoneproject.data.model.DisastertypeDataModel
import com.google.android.material.datepicker.OnSelectionChangedListener

class GridviewAdapter(
    private var items:List<DisastertypeDataModel>,
    private val onItemClickListener: (String, Boolean) -> Unit,
    private val onSelectionChanged: (Boolean) -> Unit,
    private val handleItemClick: (String, Boolean) -> Unit
):RecyclerView.Adapter<GridviewAdapter.ViewHolder>()  {
    private var original : List<DisastertypeDataModel> = items.toList()

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val image: ImageView = itemView.findViewById(R.id.image)
        val disasterType : TextView = itemView.findViewById(R.id.disastertype)
        fun bind(item: DisastertypeDataModel, onItemClickListener:(String, Boolean) -> Unit) {
            itemView.isSelected = item.isSelected
            itemView.setOnClickListener {
                item.isSelected = !item.isSelected
                itemView.isSelected = item.isSelected
                onItemClickListener(item.disasterType, item.isSelected)
            }
            image.setImageResource(item.imageResId)
            disasterType.text = item.disasterType
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_disastertype , parent,false)
        return GridviewAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun getItemList():List<DisastertypeDataModel>{
        return items
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item,{disasterType, isSelected->
            handleItemClick(disasterType, isSelected)
            val isAnyItemSelected = items.any { it.isSelected }
            onSelectionChanged(isAnyItemSelected)
        })


    }
    private fun checkAndUpdateSelectionState() {
        val isAnyItemSelected = items.any { it.isSelected }
        onSelectionChanged(isAnyItemSelected) // 선택 상태 변경 콜백 호출
    }
    fun filterByCategory(category:String){
        items = if(category == ""){
            original
        }else {
            original.filter { it.category == category }
        }
        Log.d("GridviewAdapter", "Filtered items: $items")
        notifyDataSetChanged()
    }
    fun updateList(newData:List<DisastertypeDataModel>){
        items = newData
        original= newData.toList()
        Log.d("GridviewAdapter", "Data updated: $newData")
        notifyDataSetChanged()
    }

    fun selectAllItems() {
        items.forEach { it.isSelected = true }
        notifyDataSetChanged()
    }

    // 모든 아이템 선택 해제
    fun deselectAllItems() {
        items.forEach { it.isSelected = false }
        notifyDataSetChanged()
    }
}