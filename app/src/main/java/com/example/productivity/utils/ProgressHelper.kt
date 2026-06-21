package com.example.productivity.utils

object ProgressHelper {
    fun getProgress(
        allItemsCount: Int,
        selectedItemsCount: Int
    ) : Float{
        return if(allItemsCount == 0  || selectedItemsCount == 0 ) {
            0.0f
        }else{
            selectedItemsCount.toFloat() / allItemsCount
        }
    }
}