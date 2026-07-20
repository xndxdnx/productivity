package com.example.productivity.ui.screens.newnotescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.productivity.R
import com.example.productivity.data.entities.NoteItem
import com.example.productivity.data.repository.NoteRepository
import com.example.productivity.datastore.DataStoreManager
import com.example.productivity.ui.theme.BlueLight
import com.example.productivity.ui.theme.DarkText
import com.example.productivity.ui.theme.GrayLight
import com.example.productivity.ui.theme.PurpleGrey40
import com.example.productivity.utils.UiEvent
import kotlinx.coroutines.flow.Flow

@Composable
fun NewNoteScreen(
    viewModel: NewNoteViewModel = hiltViewModel(),
    popBackStack:() -> Unit
) {
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when(event){
                is UiEvent.PopBackStack -> {
                    popBackStack()
                }
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(event.message)
                }
                else -> {}
            }
        }
    }


    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = {
            SnackbarHost(
                hostState = scaffoldState.snackbarHostState
            ) { data ->
                Snackbar(
                    snackbarData = data,
                    backgroundColor = PurpleGrey40,
                    modifier = Modifier
                        .padding(bottom = 24.dp)
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .padding(paddingValues)
                .background(color = GrayLight)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp),
                shape = RoundedCornerShape(14.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        TextField(
                            modifier = Modifier
                                .weight(1f),
                            value = viewModel.title,
                            onValueChange = { viewModel.onEvent(NewNoteEvent.OnTitleChange(it)) },
                            label = {
                                Text(
                                    text = "title...",
                                    fontSize = 14.sp,
                                )
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = BlueLight,
                                disabledIndicatorColor = Color.Transparent,
//                                focusedContainerColor = Color.White,
//                                unfocusedContainerColor = Color.White
                            ),

                            singleLine = true,

                            textStyle = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(
                                    viewModel.titleColor.toColorInt()
                                )

                            )
                        )

                        IconButton(
                            onClick = {
                                viewModel.onEvent(NewNoteEvent.OnSave)
                            }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.save_ic),
                                contentDescription = null,
                                tint = GrayLight
                            )
                        }

                    }

                    TextField(
                        modifier = Modifier
                            .padding(top = 5.dp),
                        value = viewModel.description,
                        onValueChange = { viewModel.onEvent(NewNoteEvent.OnDescriptionChange(it)) },
                        label = {
                            Text(
                                text = "description...",
                                fontSize = 14.sp,
                            )
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = BlueLight,
                            disabledIndicatorColor = Color.Transparent,
//                                focusedContainerColor = Color.White,
//                                unfocusedContainerColor = Color.White
                        ),

                        textStyle = TextStyle(
                            fontSize = 14.sp,
                            color = DarkText

                        )
                    )
                }
            }
        }

    }


}


@Preview
@Composable
fun NewNoteScreenPreview() {

    val context = LocalContext.current

    val previewViewModel: NewNoteViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return NewNoteViewModel(
                    repository = object : NoteRepository {
                        override suspend fun insertItem(note: NoteItem) {}
                        override suspend fun deleteItem(note: NoteItem) {}
                        override fun getAllItems(): Flow<List<NoteItem>> {
                           TODO()
                        }

                        override suspend fun getNoteItemById(id: Int) = com.example.productivity.data.entities.NoteItem(null, "", "", "")
                    },

                    dataStoreManager = DataStoreManager(context),
                    savedStateHandle = SavedStateHandle()
                ) as T
            }
        }
    )

    NewNoteScreen(
        viewModel = previewViewModel,
        popBackStack = {}
    )
}