package com.daepiro.numberoneproject.data.model

data class DisastertypeDataModel(
    val category:String ="",
    val disasterType:String="",
    val imageResId:Int,
    var isSelected:Boolean = false
)
