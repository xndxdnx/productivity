package com.example.productivity.ui.screens.mainscreen

import com.example.productivity.R
import com.example.productivity.utils.Routes

sealed class BottomNavItem(
    val title: String,
    val route: String,
    val icon: Int
) {
    object ShoppingListItem : BottomNavItem(title = "Shopping", Routes.SHOPPING_LIST,R.drawable.book_ic)
    object AboutItem : BottomNavItem(title = "About", Routes.ABOUT,R.drawable.info_ic)
    object SettingsItem : BottomNavItem(title = "Settings",Routes.SETTINGS,R.drawable.settings_ic)
    object NoteListItem : BottomNavItem(title = "Notes", Routes.NOTE_LIST,R.drawable.pen_ic)
    
}