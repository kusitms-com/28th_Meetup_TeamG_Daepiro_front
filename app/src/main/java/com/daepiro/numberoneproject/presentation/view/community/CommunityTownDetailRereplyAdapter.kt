package com.daepiro.numberoneproject.presentation.view.community

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.daepiro.numberoneproject.R
import com.daepiro.numberoneproject.data.model.ChildModel
import com.daepiro.numberoneproject.data.model.ConversationModel

class CommunityTownDetailRereplyAdapter(
    private var items:List<ChildModel>
):RecyclerView.Adapter<CommunityTownDetailRereplyAdapter.ViewHolder>() {
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val info: TextView = itemView.findViewById(R.id.info)
        val content:TextView = itemView.findViewById(R.id.content)
        val likeNum : TextView = itemView.findViewById(R.id.like_num)
        val good: ImageView = itemView.findViewById(R.id.like_btn_good)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_community_replylist, parent,false)
        return CommunityTownDetailRereplyAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(position < items.size){
            val item = items[position]
            holder.info.text = item.info
            holder.content.text = item.content
            holder.likeNum.text = item.like.toString()
            if(item.like ==0){
                holder.likeNum.visibility = View.GONE
            }
            else{
                //색상 변화
                holder.good.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(holder.good.context,R.color.orange_500))
            }
        }
    }
    fun updateList(newData:List<ChildModel>){
        items = newData
        notifyDataSetChanged()
    }
}