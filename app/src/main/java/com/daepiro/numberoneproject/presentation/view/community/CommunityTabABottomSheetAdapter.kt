package com.daepiro.numberoneproject.presentation.view.community

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.daepiro.numberoneproject.R
import com.daepiro.numberoneproject.data.model.ConversationModel
import com.daepiro.numberoneproject.data.model.ConversationRequestBody

class CommunityTabABottomSheetAdapter(
    private var items:List<ConversationModel>,
    private val listener:onItemClickListener
):RecyclerView.Adapter<CommunityTabABottomSheetAdapter.ViewHolder>() {
    private lateinit var subadapter: CommunityTownDetailRereplyAdapter
    interface onItemClickListener{
        fun onItemClickListener()
    }
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val info : TextView = itemView.findViewById(R.id.user_info)
        val content:TextView = itemView.findViewById(R.id.content)
        val recycler : RecyclerView = itemView.findViewById(R.id.recycler)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_community_commentlist,parent,false)
        return CommunityTabABottomSheetAdapter.ViewHolder(view)
    }


    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        subadapter = CommunityTownDetailRereplyAdapter(emptyList())
        holder.recycler.adapter = subadapter
        if(position < items.size){
            val item = items[position]
            holder.info.text = item.info
            holder.content.text = item.content
            subadapter.updateList(item.childs)
            holder.itemView.setOnClickListener{
                listener.onItemClickListener()//그냥 배경만 활성화되는 정도
            }
        }

    }


    fun updateList(newData:List<ConversationModel>){
        items = newData
        notifyDataSetChanged()
    }
}