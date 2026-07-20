package com.example.productivity.ui.screens.newnotescreen

sealed class NewNoteEvent {

    data class OnTitleChange(val text: String) : NewNoteEvent()
    data class OnDescriptionChange(val text: String) : NewNoteEvent()
    object OnSave : NewNoteEvent()
}