package com.example.productivity.ui.screens.settingsscreen

sealed class SettingsEvent {
    data class OnItemClick(val color: String ) : SettingsEvent()
}