package com.example.productivity.ui.screens.notelistscreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productivity.data.entities.NoteItem
import com.example.productivity.data.repository.NoteRepository
import com.example.productivity.datastore.DataStoreManager
import com.example.productivity.dialog.DialogController
import com.example.productivity.dialog.DialogEvent
import com.example.productivity.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val repository: NoteRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel(), DialogController {

    private val _uiEvent = Channel<UiEvent>()

    val uiEvent = _uiEvent.receiveAsFlow()

    override var dialogTitle = mutableStateOf<String>("Delete This Note")
        private set
    override var showEditableText = mutableStateOf<Boolean>(false)
        private set
    override var editableText = mutableStateOf<String>("")
        private set
    override var openDialog = mutableStateOf<Boolean>(false)
        private set

    private var noteItem: NoteItem? = null

    var noteList by mutableStateOf(listOf<NoteItem>())

    var searchText by mutableStateOf("")
        private set

    var originalNoteList = listOf<NoteItem>()

    var noteListFlow  = repository.getAllItems()

    var titleColor by mutableStateOf ("#FFB388FF")

    init {
        viewModelScope.launch {
            dataStoreManager.getStringPreference(
                key = DataStoreManager.TITLE_COLOR,
                defValue = "#FFB388FF"
            ).collect{ color ->
                titleColor = color
            }
        }

        viewModelScope.launch {
            noteListFlow.collect { list ->
                noteList = list
                originalNoteList = list
            }
        }
    }

    fun onEvent(event: NoteListEvent) {
        when (event) {
            is NoteListEvent.OnNoteClick -> {
                sendUiEvent(UiEvent.Navigate(event.route))
            }

            is NoteListEvent.OnShowDeleteDialog -> {
                openDialog.value = true
                noteItem = event.item
            }

            is NoteListEvent.UnDoneDeleteItem -> {
                viewModelScope.launch {
                    repository.insertItem(noteItem!!)
                }
            }

            is NoteListEvent.OnTextSearchChange -> {
                searchText = event.text
                noteList = originalNoteList.filter{ note ->
                    note.title.lowercase()
                        .startsWith(searchText.lowercase())
                }
            }
        }
    }

    override fun onDialogEvent(event: DialogEvent) {
       when(event){
           is DialogEvent.OnCancel -> {
               openDialog.value = false
           }
           is DialogEvent.OnConfirm -> {
               viewModelScope.launch {
                   repository.deleteItem(noteItem!!)
                   sendUiEvent(UiEvent.ShowSnackBar("Undone delete Item"))
               }
               openDialog.value = false
           }
           else -> {}
       }
    }
    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}