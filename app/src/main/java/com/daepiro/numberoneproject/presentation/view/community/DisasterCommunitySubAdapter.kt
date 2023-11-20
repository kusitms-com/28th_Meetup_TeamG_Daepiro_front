package com.daepiro.numberoneproject.presentation.view.community

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.daepiro.numberoneproject.R
import com.daepiro.numberoneproject.data.model.CommunityHomeConversationModel

class DisasterCommunitySubAdapter(
    private val context: Context,
    private var items:List<CommunityHomeConversationModel>,
    private val listener:onItemClickListener
):RecyclerView.Adapter<DisasterCommunitySubAdapter.ViewHolder>() {
    interface onItemClickListener{
        fun onItemClickListener()
    }
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val info: TextView = itemView.findViewById(R.id.info)
        val content:TextView = itemView.findViewById(R.id.content)
        val good: ImageView = itemView.findViewById(R.id.good)
        val like:TextView = itemView.findViewById(R.id.like)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_community_homelist,parent,false)
        return DisasterCommunitySubAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(position<items.size){
            val item = items[position]
            holder.info.text = item.info
            holder.content.text = item.content
            holder.like.text = item.like.toString()
            if(item.like > 0){
                val color = ContextCompat.getColor(context,R.color.orange_500)
                holder.good.setColorFilter(color)
                holder.like.visibility = View.VISIBLE
            }
        }
    }

    fun updateList(newData:List<CommunityHomeConversationModel>){
        items=newData
        notifyDataSetChanged()
    }
}