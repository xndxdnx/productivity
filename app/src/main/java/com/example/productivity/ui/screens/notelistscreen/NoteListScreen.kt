package com.example.productivity.ui.screens.notelistscreen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.productivity.dialog.MainDialog
import com.example.productivity.ui.theme.GrayLightSoft
import com.example.productivity.ui.theme.PurpleGrey40
import com.example.productivity.utils.UiEvent


@Composable
fun NoteListScreen(
    viewModel: NoteListViewModel = hiltViewModel(),
    onNavigate:(String) -> Unit,
){
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when(event){
                is UiEvent.Navigate -> {
                    onNavigate(event.route)
                }
                is UiEvent.ShowSnackBar -> {
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = "Undone"
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.onEvent(NoteListEvent.UnDoneDeleteItem)
                    }
                }
                else -> {}
            }
        }
    }

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = {
            SnackbarHost(
                hostState = scaffoldState.snackbarHostState
            ){ data ->
                Snackbar(
                    snackbarData = data,
                    backgroundColor = PurpleGrey40,
                    modifier = Modifier
                        .padding(bottom = 50.dp)
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(GrayLightSoft)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = viewModel.searchText,
                    onValueChange = { NoteListEvent.OnTextSearchChange(it)},
                    label = {
                        Text(
                            text = "Search..."
                        )
                    }
                )

            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(bottom = 100.dp)
            ) {
                items(viewModel.noteList) { item ->
                    NoteUiItem(
                      item = item,
                        titleColor = viewModel.titleColor,
                        onEvent = viewModel::onEvent
                    )
                }
            }

            MainDialog(viewModel)

            if (viewModel.noteList.isEmpty()){
                Text(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentHeight()
                    ,
                    text = "Empty",
                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    color = Color.LightGray
                )
            }
        }
    }

}