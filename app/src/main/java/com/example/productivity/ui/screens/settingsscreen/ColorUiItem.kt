package com.example.productivity.ui.screens.settingsscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.productivity.R

@Composable
fun ColorUiItem(
    colorItem: ColorItem,
    onEvent: (SettingsEvent) -> Unit
){

    IconButton(
        modifier = Modifier
            .padding(start = 10.dp)
            .clip(CircleShape)
            .size(35.dp)
            .background( color = Color(
                android.graphics.Color.parseColor(
                    colorItem.color
                )
            )),
        onClick = {onEvent(SettingsEvent.OnItemClick(colorItem.color))}
    ) {
        if (colorItem.isSelected){
            Icon(
                painter = painterResource(R.drawable.check_ic),
                contentDescription = null,
                tint = Color.White
            )
        }

    }


}