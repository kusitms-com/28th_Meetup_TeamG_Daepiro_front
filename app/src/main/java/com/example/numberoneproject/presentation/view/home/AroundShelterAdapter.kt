package com.example.numberoneproject.presentation.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.numberoneproject.databinding.ItemAroundShelterBinding

class AroundShelterAdapter: RecyclerView.Adapter<AroundShelterAdapter.CustomViewHolder>() {
    inner class CustomViewHolder(val binding: ItemAroundShelterBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind() {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AroundShelterAdapter.CustomViewHolder {
        val view = ItemAroundShelterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: AroundShelterAdapter.CustomViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount() = 5
}