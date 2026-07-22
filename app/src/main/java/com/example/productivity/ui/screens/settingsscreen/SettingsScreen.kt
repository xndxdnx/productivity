package com.example.productivity.ui.screens.settingsscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.Span
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.productivity.utils.ColorUtils.colorList
import java.nio.file.WatchEvent

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
){

    val colorList = viewModel.colorItemListState

    Column(
      modifier = Modifier
          .fillMaxSize()
          .padding(16.dp)
          .systemBarsPadding()
    ) {
        Text(
            text = "Title Color..",
            fontSize = 16.sp,
        )

        Text(
            text = "Selected title color",
            fontSize = 12.sp,
            color = Color.Gray
        )
        LazyRow(
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
        ) {
            items(colorList) { item ->
                ColorUiItem(
                    colorItem = item,
                ) { event ->
                    viewModel.onEvent(event)
                }


            }
        }

    }



}