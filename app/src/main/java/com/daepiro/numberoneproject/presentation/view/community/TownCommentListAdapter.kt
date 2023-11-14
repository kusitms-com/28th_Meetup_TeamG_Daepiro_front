package com.daepiro.numberoneproject.presentation.view.community

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.daepiro.numberoneproject.R
import com.daepiro.numberoneproject.data.model.CommunityTownListModel
import com.daepiro.numberoneproject.data.model.Content

class TownCommentListAdapter(
    private var items: List<Content>,
    private val listener: onItemClickListener
):RecyclerView.Adapter<TownCommentListAdapter.ViewHolder>() {
    interface onItemClickListener{
        fun onItemClick(id:Int)
    }
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val tag : TextView = itemView.findViewById(R.id.tag)
        val title:TextView = itemView.findViewById(R.id.title)
        val content:TextView = itemView.findViewById(R.id.content)
        val image : ImageView =itemView.findViewById(R.id.image)
        val writerInfo:TextView = itemView.findViewById(R.id.writer_info)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_community_townlist,parent,false)
        return TownCommentListAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(position < items.size){
            val item = items[position]
            holder.title.text = item.title
            holder.tag.text = item.tag
            holder.content.text = item.content
            holder.writerInfo.text = " ${item.ownerNickName} ∙ ${item.createdAt}"
            Glide.with(holder.itemView.context)
                .load(item.thumbNailImageUrl)
                .into(holder.image)
        }
    }
    fun updateList(newData:List<Content>){
        this.items = newData
        notifyDataSetChanged()
    }

}