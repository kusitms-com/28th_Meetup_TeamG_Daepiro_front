package com.daepiro.numberoneproject.presentation.view.family

import android.content.res.ColorStateList
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.daepiro.numberoneproject.R
import com.daepiro.numberoneproject.data.model.FamilyListResponse
import com.daepiro.numberoneproject.databinding.ItemFamilyListBinding
import com.daepiro.numberoneproject.presentation.view.funding.main.FundingListAdapter

class FamilyListAdapter: RecyclerView.Adapter<FamilyListAdapter.CustomViewHolder>() {
    private lateinit var itemClickListener: OnItemClickListener
    private var familyList =  listOf<FamilyListResponse>()
    private var isManageMode = false

    inner class CustomViewHolder(private val binding: ItemFamilyListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FamilyListResponse) {
            binding.model = item

            if (item.isSafety) {
                binding.tvSafetyState.apply {
                    text = itemView.context.getString(R.string.안전해요)
                    setTextColor(ContextCompat.getColor(itemView.context, R.color.green))
                }
                binding.ivSafetyState.apply {
                    setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.ic_good))
                    imageTintList = ColorStateList.valueOf(ContextCompat.getColor(itemView.context, R.color.green))
                }
                binding.llSafetyState.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(itemView.context, R.color.light_green))
            } else {
                binding.tvSafetyState.apply {
                    text = itemView.context.getString(R.string.위험해요)
                    setTextColor(ContextCompat.getColor(itemView.context, R.color.warning))
                }
                binding.ivSafetyState.apply {
                    setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.ic_warning))
                    imageTintList = ColorStateList.valueOf(ContextCompat.getColor(itemView.context, R.color.warning))
                }
                binding.llSafetyState.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(itemView.context, R.color.light_waring))
            }

            if (item.session) {
                binding.ivOnlineState.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.ic_online))
            } else {
                binding.ivOnlineState.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.ic_offline))
            }

            if (isManageMode) {
                binding.ivManage.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.ic_delete_orange))
            } else {
                binding.ivManage.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.ic_send_orange))
            }

            binding.ivManage.setOnClickListener {
                itemClickListener.onClickManage(it, position, familyList[position])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = ItemFamilyListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(familyList[position])

        holder.itemView.setOnClickListener {
            itemClickListener.onClickItem(it, position, familyList[position])
        }
    }

    fun setData(newData: List<FamilyListResponse>) {
        familyList = newData
        notifyDataSetChanged()
    }

    fun changeManageMode() {
        isManageMode = !isManageMode
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onClickItem(v: View, position: Int, familyInfo: FamilyListResponse)
        fun onClickManage(v: View, position: Int, familyInfo: FamilyListResponse)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    override fun getItemCount() = familyList.size
}