package com.example.productivity.ui.screens.additemscreen

import com.example.productivity.data.entities.AddItem

sealed class AddItemEvent {
    data class OnTextChange(val text: String) : AddItemEvent()
    object OnItemSave : AddItemEvent()
    data class OnItemDelete (val item: AddItem) : AddItemEvent()
    data class OnCheckedChange (val item: AddItem) : AddItemEvent()
    
    data class OnShowEditDialog (val item: AddItem): AddItemEvent()
}