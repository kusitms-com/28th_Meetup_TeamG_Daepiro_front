package com.example.numberoneproject.presentation.view.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.numberoneproject.data.model.Shelter
import com.example.numberoneproject.databinding.ItemAroundShelterBinding
import com.example.numberoneproject.databinding.ItemDisasterCheckListBinding
import com.example.numberoneproject.presentation.view.funding.FundingCategoryAdapter

class DisasterCheckListAdapter: RecyclerView.Adapter<DisasterCheckListAdapter.CustomViewHolder>() {
    inner class CustomViewHolder(val binding: ItemDisasterCheckListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind() {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DisasterCheckListAdapter.CustomViewHolder {
        val view = ItemDisasterCheckListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: DisasterCheckListAdapter.CustomViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount() = 5
}