package com.daepiro.numberoneproject.presentation.view.community

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.daepiro.numberoneproject.R

class CommunityTownDetailImageAdapter(
    private var items: List<String>
): RecyclerView.Adapter<CommunityTownDetailImageAdapter.ViewHolder>() {
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val imageView : ImageView = itemView.findViewById(R.id.image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_community_town_picture,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val url = items[position]
        Glide.with(holder.imageView.context)
            .load(url)
            .into(holder.imageView)
    }
    fun updateList(newData:List<String>){
        items = newData
        notifyDataSetChanged()
    }
}