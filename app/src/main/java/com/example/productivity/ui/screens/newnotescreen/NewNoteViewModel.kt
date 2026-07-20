package com.example.productivity.ui.screens.newnotescreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productivity.data.entities.NoteItem
import com.example.productivity.data.repository.NoteRepository
import com.example.productivity.datastore.DataStoreManager
import com.example.productivity.utils.UiEvent
import com.example.productivity.utils.getCurrentTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class NewNoteViewModel @Inject constructor(
    private val repository: NoteRepository,
    private val dataStoreManager: DataStoreManager,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()

    val uiEvent = _uiEvent.receiveAsFlow()

    var title by mutableStateOf("")
        private set
    var description by mutableStateOf("")
        private set

    var item: NoteItem? = null

    private var noteId = -1

    var titleColor by mutableStateOf("#FFB388FF")

    init {
        val noteIdString = savedStateHandle.get<String>("noteId")

        noteId = noteIdString?.toIntOrNull() ?: -1

        if (noteId != -1 && noteId > 0) {
            viewModelScope.launch {
                repository.getNoteItemById(noteId).let { noteItem ->

                    title = noteItem.title
                    description = noteItem.description

                    this@NewNoteViewModel.item = noteItem
                }
                dataStoreManager.getStringPreference(
                    key = DataStoreManager.TITLE_COLOR,
                    defValue = "#FFB388FF"
                ).collect { color ->
                    titleColor = color
                }
            }
        }
    }


    fun onEvent(event: NewNoteEvent) {
        when (event) {
            is NewNoteEvent.OnTitleChange -> {
                title = event.text
            }

            is NewNoteEvent.OnDescriptionChange -> {
                description = event.text
            }

            is NewNoteEvent.OnSave -> {
                viewModelScope.launch {

                    if (title.isBlank()) {
                        sendUiEvent(UiEvent.ShowSnackBar("Title can't be Empty"))
                        return@launch
                    }

                    repository.insertItem(
                        NoteItem(
                            id = item?.id,
                            title = title,
                            description = description,
                            time = item?.time ?: getCurrentTime()
                        )
                    )

                    sendUiEvent(UiEvent.PopBackStack)

                }
            }


        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }


}