package com.example.productivity.ui.screens.notelistscreen

import com.example.productivity.data.entities.NoteItem

sealed class NoteListEvent {
    data class OnNoteClick(val route: String) : NoteListEvent()
    data class OnShowDeleteDialog (val item: NoteItem) : NoteListEvent()
    object UnDoneDeleteItem : NoteListEvent()
    data class OnTextSearchChange(val text: String) : NoteListEvent()
}