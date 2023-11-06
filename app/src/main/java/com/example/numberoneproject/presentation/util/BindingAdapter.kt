package com.example.numberoneproject.presentation.util

import android.view.View
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.numberoneproject.R

object BindingAdapter {
    @BindingAdapter("setDisasterLevel")
    @JvmStatic
    fun setDisasterLevel(view : CardView, level: Int){
        when(view.id) {
            R.id.cv_disaster_level_1 -> {
                if (level in 1..3) {
                    view.setCardBackgroundColor(ContextCompat.getColor(view.context, R.color.orange_500))
                } else {
                    view.setCardBackgroundColor(ContextCompat.getColor(view.context, R.color.secondary_50))
                }
            }
            R.id.cv_disaster_level_2 -> {
                if (level in 2..3) {
                    view.setCardBackgroundColor(ContextCompat.getColor(view.context, R.color.orange_500))
                } else {
                    view.setCardBackgroundColor(ContextCompat.getColor(view.context, R.color.secondary_50))
                }
            }
            R.id.cv_disaster_level_3 -> {
                if (level == 3) {
                    view.setCardBackgroundColor(ContextCompat.getColor(view.context, R.color.orange_500))
                } else {
                    view.setCardBackgroundColor(ContextCompat.getColor(view.context, R.color.secondary_50))
                }
            }
        }
    }

    @BindingAdapter("loadImage")
    @JvmStatic
    fun setThumbnailImage(imageView : ImageView, url : String){
        Glide.with(imageView.context).load(url).into(imageView)
    }
}