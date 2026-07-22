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

    val colorList = listOf(
        "#FFB388FF",
        "#FF82B1FF",
        "#FF80D8FF",
        "#FFFF80AB",
        "#FFE680C8",
        "#FF80C8FF",
        "#FFA8A8FF",
        "#FFFF80FF",
        "#FFFFFF80",
        "#FFFFD080",
        "#FF80FFA8",
        "#FFFFB080",
        "#FFFFA8FF",
        "#FFA8FF80"
    )
}