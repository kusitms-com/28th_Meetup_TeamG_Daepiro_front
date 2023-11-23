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
    private var items: List<Content> = listOf(),
    private val listener: onItemClickListener,
    private val getTimeDifference: (String) -> String
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
        val likenum:TextView = itemView.findViewById(R.id.like_num)
        val replynum:TextView = itemView.findViewById(R.id.reply_num)


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
            holder.tag.text = when(item.tag.toString()){
                "LIFE" -> "일상"
                "TRAFFIC" -> "교통"
                "SAFETY" -> "치안"
                 "NONE" -> "기타"
                else-> "전체"
            }
            holder.content.text = item.content
            //시간 처리
            val time = getTimeDifference(item.createdAt)
            holder.writerInfo.text = " ${item.address} ∙ ${item.ownerNickName} ∙ ${time}"

            Glide.with(holder.itemView.context)
                .load(item.thumbNailImageUrl)
                .into(holder.image)

            holder.likenum.text = item.articleLikeCount.toString()
            holder.replynum.text = item.commentCount.toString()
            holder.itemView.setOnClickListener{
                listener.onItemClick(item.id)
            }


        }

    }
    fun updateList(newData:List<Content>){
        val startIndox = items.size
        items = items + newData
        notifyItemRangeChanged(startIndox,newData.size)
    }
    fun clearData(){
        items = listOf()
        notifyDataSetChanged()
    }

}