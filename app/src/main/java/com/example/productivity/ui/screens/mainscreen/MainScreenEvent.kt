package com.example.productivity.ui.screens.mainscreen

sealed class MainScreenEvent {
    object OnItemSave : MainScreenEvent() // Срабатывает когда пользователь подтверждает ввод
    data class Navigate(val route: String) : MainScreenEvent()
    data class NavigateMain(val route: String) : MainScreenEvent()
    data class OnNewItemClick(val route: String) : MainScreenEvent()
}