package com.example.productivity.ui.screens.additemscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.productivity.R
import com.example.productivity.dialog.MainDialog
import com.example.productivity.ui.theme.DarkText
import com.example.productivity.ui.theme.PurpleGrey40
import com.example.productivity.utils.UiEvent
import kotlinx.coroutines.flow.flowOf

@Composable
fun AddItemScreen(
    viewModel: AddItemViewModel = hiltViewModel()
){

    val snackBarHostState = remember { SnackbarHostState() }

    val itemList = (viewModel.itemList ?: flowOf(emptyList())).collectAsState(emptyList())

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when(event){
                is UiEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(
                        event.message
                    )
                }
                else -> {}
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState
            ) { data ->
                Snackbar(
                    snackbarData = data,
                    contentColor = PurpleGrey40,
                    modifier = Modifier
                        .padding(bottom = 24.dp)
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .padding(paddingValues)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        modifier = Modifier
                            .weight(1f),
                        value = viewModel.editableText.value,
                        onValueChange = { newValue ->
                            viewModel.onEvent(AddItemEvent.OnTextChange(text = newValue))
                        },
                        label = {
                            Text(
                                text = "New Item",
                                fontSize = 12.sp,
                            )

                        },
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            color = DarkText,
                        ),
                        singleLine = true
                    )
                    IconButton(
                        onClick = {
                            viewModel.onEvent(AddItemEvent.OnItemSave)
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.add_ic),
                            contentDescription = null,
                        )
                    }

                }


            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 5.dp, end = 5.dp)
            ) {
                items(itemList.value) { item ->
                    AddItemUi(
                        item = item,
                        onEvent = { event ->
                            viewModel.onEvent(event)
                        }
                    )
                }

            }

            if (itemList.value.isEmpty()){
                Text(
                    text = "Empty",
                    fontSize = 30.sp,
                    color = Color.LightGray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentHeight()
                )
            }
        }

        MainDialog(viewModel)
    }

}