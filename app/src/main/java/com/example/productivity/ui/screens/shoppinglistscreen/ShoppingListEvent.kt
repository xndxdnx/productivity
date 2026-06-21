package com.example.productivity.ui.screens.shoppinglistscreen

import com.example.productivity.data.entities.ShoppingListItem

sealed class ShoppingListEvent {
    data class OnItemClick(val route: String): ShoppingListEvent()
    data class OnShowEditDialog(val item: ShoppingListItem): ShoppingListEvent()
    data class OnShowDeleteDialog(val item: ShoppingListItem): ShoppingListEvent()
    object OnItemSave: ShoppingListEvent()
}