package com.daepiro.numberoneproject.presentation.util

import android.location.Geocoder
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.util.Locale

object BindingAdapter {
    @BindingAdapter(value = ["latitude", "longitude"], requireAll = true)
    @JvmStatic
    fun setLatAndLogToAddress(textView: TextView, latitude: Double?, longitude: Double?): String {
        val geocoder = Geocoder(textView.context, Locale.KOREAN)
        val address = geocoder.getFromLocation(latitude!!, longitude!!, 1)

        return if (address.isNullOrEmpty()) {
            "주소 정보가 올바르지 않습니다."
        } else {
            address[0]?.getAddressLine(0).toString().replace("대한민국 ", "")
        }
    }

    @BindingAdapter("loadImage")
    @JvmStatic
    fun setThumbnailImage(imageView : ImageView, url : String){
        Glide.with(imageView.context).load(url).into(imageView)
    }
}