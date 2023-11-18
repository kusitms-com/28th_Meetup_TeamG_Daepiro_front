package com.daepiro.numberoneproject.presentation.view.community

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.daepiro.numberoneproject.R
import com.daepiro.numberoneproject.data.model.CommunityTownReplyResponseItem

class CommunityTownDetailReplyAdapter(
    private var items:List<CommunityTownReplyResponseItem> = listOf(),
    private val listener : onItemClickListener
):RecyclerView.Adapter<CommunityTownDetailReplyAdapter.ViewHolder>() {

    interface onItemClickListener{
        fun onAdditionalItemClick(articleid: Int)
        fun onReplyClick(commentid:Long)
    }
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val userInfo: TextView = itemView.findViewById(R.id.user_info)
        val content : TextView = itemView.findViewById(R.id.content)
        val likenum:TextView = itemView.findViewById(R.id.like_num)
        val likebtn: ImageView = itemView.findViewById(R.id.like_btn)
        val additional:ImageView = itemView.findViewById(R.id.additional)
        val rereply:ImageView = itemView.findViewById(R.id.rereply_btn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_community_commentlist,parent,false)
        return CommunityTownDetailReplyAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val color = ContextCompat.getColor(holder.itemView.context, R.color.orange_500)
        if(position< items.size){
            val item = items[position]
            holder.userInfo.text = "${item.authorNickName} ∙ ${item.createdAt}"
            holder.content.text = item.content
            holder.likenum.text = item.likeCount.toString()
            if(item.likeCount > 0){
                DrawableCompat.wrap(holder.likebtn.drawable).also{wrappedDrawable->
                    DrawableCompat.setTint(wrappedDrawable,color)
                    holder.likebtn.setImageDrawable(wrappedDrawable)
                }
            }
            holder.additional.setOnClickListener{
                listener.onAdditionalItemClick(position)
            }
            holder.rereply.setOnClickListener{
                listener.onReplyClick(item.commentId)
            }
        }


    }
    fun updateList(newData:List<CommunityTownReplyResponseItem>){
        val startIndex = items.size
        items = items+newData
        notifyItemRangeChanged(startIndex,newData.size)
    }
}