package com.example.productivity.utils

import androidx.compose.ui.graphics.Color
import com.example.productivity.ui.theme.Green
import com.example.productivity.ui.theme.Red
import com.example.productivity.ui.theme.Yellow

object ColorUtils {
    fun getProgressColor(
        progress: Float
    ) : Color{
        return when {
            progress < 0.34f -> Red
            progress < 0.67f -> Yellow
            else -> Green
        }
    }
}