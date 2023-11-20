package com.daepiro.numberoneproject.presentation.view.community

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.daepiro.numberoneproject.R
import com.daepiro.numberoneproject.data.model.CommunityHomeSituationModel

class DisasterCommunityMainAdapter(
    private val context: Context,
    private var items:List<CommunityHomeSituationModel>,
    private val listener:onItemClickListener
): RecyclerView.Adapter<DisasterCommunityMainAdapter.ViewHolder>() {
    interface onItemClickListener{
        fun onItemClickListener(disasterId:Int)
    }
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val disasterType: TextView = itemView.findViewById(R.id.disasterType)
        val title: TextView = itemView.findViewById(R.id.title)
        val msg: TextView = itemView.findViewById(R.id.msg)
        val info: TextView = itemView.findViewById(R.id.info)
        val recycler:RecyclerView = itemView.findViewById(R.id.recycler_reply)
        val additional : TextView = itemView.findViewById(R.id.additional)
        //없을때 인비저블
        val conversationCnt : TextView = itemView.findViewById(R.id.conversationCnt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_community_main, parent,false)
        return DisasterCommunityMainAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(position < items.size){
            val item= items[position]
            holder.disasterType.text = item.disasterType
            holder.title.text = item.title
            holder.msg.text = item.msg
            holder.info.text = item.info
            val conversationCnt = item.conversationCnt
            if(conversationCnt != 0){
                holder.conversationCnt.visibility = View.VISIBLE
                holder.conversationCnt.text = item.conversationCnt.toString()
            }
            else{
                //댓글 없을때
                holder.additional.visibility = View.GONE
            }
            //내부 recycler
            val subAdapter = DisasterCommunitySubAdapter(context,item.conversations, object : DisasterCommunitySubAdapter.onItemClickListener{
                override fun onItemClickListener() {
                    //필요한가..?
                }

            })
            holder.recycler.adapter = subAdapter
            holder.additional.setOnClickListener{
                listener.onItemClickListener(item.disasterId)
            }
        }
    }

    fun updateList(newData:List<CommunityHomeSituationModel>){
        items = newData
        notifyDataSetChanged()
    }
}