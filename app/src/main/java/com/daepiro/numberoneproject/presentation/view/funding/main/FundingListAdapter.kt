package com.daepiro.numberoneproject.presentation.view.funding.main

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.daepiro.numberoneproject.data.model.FundingInfo
import com.daepiro.numberoneproject.databinding.ItemFundingListBinding
import kotlin.math.roundToInt

class FundingListAdapter: RecyclerView.Adapter<FundingListAdapter.CustomViewHolder>() {
    private lateinit var itemClickListener: OnItemClickListener
    private var fundingList = listOf<FundingInfo>()

    inner class CustomViewHolder(private val binding: ItemFundingListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FundingInfo) {
            binding.model = item

//            val originalText = itemView.context.getString(R.string._님이_참여중인_후원이에요_).format(item.)
//            val spannableString = SpannableString(originalText)
//            // 문자열에서 강조할 부분의 시작 인덱스 찾기
//            val startIndex = originalText.indexOf(it.nickname)
//
//            spannableString.setSpan(
//                ForegroundColorSpan(ContextCompat.getColor(itemView.context, R.color.orange_500)),
//                startIndex,
//                startIndex + it.nickname.length,
//                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//            )
//            binding.tv.text = spannableString

            binding.tvProgress.text = "${(item.currentHeart * 100 / item.targetHeart)}%"

            var progressBarWidth = 0
            binding.pbFunding.viewTreeObserver.addOnGlobalLayoutListener {
                progressBarWidth = binding.pbFunding.width

                val newMarginLeft = (binding.pbFunding.progress * progressBarWidth / binding.pbFunding.max).toFloat()
                val params = binding.ivChar.layoutParams as ConstraintLayout.LayoutParams

                params.leftMargin = if (newMarginLeft < 100) {
                    newMarginLeft.roundToInt()
                } else if (newMarginLeft > 800) {
                    newMarginLeft.roundToInt() - 80
                } else {
                    newMarginLeft.roundToInt() - 25
                }
                binding.ivChar.layoutParams = params
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = ItemFundingListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(fundingList[position])

        holder.itemView.setOnClickListener {
            itemClickListener.onClickItem(it, position, fundingList[position].id)
        }
    }

    fun setData(newData: List<FundingInfo>) {
        fundingList = newData
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onClickItem(v: View, position: Int, sponsorId: Int)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    override fun getItemCount() = fundingList.size
}