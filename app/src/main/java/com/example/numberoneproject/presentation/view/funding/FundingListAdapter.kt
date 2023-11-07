package com.example.numberoneproject.presentation.view.funding

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalFocusChangeListener
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.numberoneproject.databinding.ItemFundingListBinding
import kotlin.math.roundToInt

class FundingListAdapter: RecyclerView.Adapter<FundingListAdapter.CustomViewHolder>() {
    private lateinit var itemClickListener: OnItemClickListener

    inner class CustomViewHolder(private val binding: ItemFundingListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Any) {
            var progressBarWidth = 0

            binding.pbFunding.viewTreeObserver.addOnGlobalLayoutListener {
                progressBarWidth = binding.pbFunding.width

                val newMarginLeft = (binding.pbFunding.progress * progressBarWidth / binding.pbFunding.max).toFloat()
                val params = binding.ivChar.layoutParams as ConstraintLayout.LayoutParams
                params.leftMargin = if (newMarginLeft <10) newMarginLeft.roundToInt() else newMarginLeft.roundToInt() - 20
                binding.ivChar.layoutParams = params
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FundingListAdapter.CustomViewHolder {
        val view = ItemFundingListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: FundingListAdapter.CustomViewHolder, position: Int) {
        holder.bind(position)

        holder.itemView.setOnClickListener {
            itemClickListener.onClickItem(it, position)
        }
    }

    interface OnItemClickListener {
        fun onClickItem(v: View, position: Int)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    override fun getItemCount() = 7
}