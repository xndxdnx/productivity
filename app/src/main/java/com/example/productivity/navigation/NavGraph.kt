package com.example.productivity.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.productivity.ui.screens.aboutscreen.AboutScreen
import com.example.productivity.ui.screens.notelistscreen.NoteListScreen
import com.example.productivity.ui.screens.settingsscreen.SettingsScreen
import com.example.productivity.ui.screens.shoppinglistscreen.ShoppingListScreen
import com.example.productivity.utils.Routes

@Composable
fun NavGraph (
    navHostController: NavHostController
) {
    
    NavHost(
        navController = navHostController,
        startDestination = Routes.SHOPPING_LIST
    ) {
        composable(route = Routes.SHOPPING_LIST) {ShoppingListScreen()}
        composable(route = Routes.ABOUT) {AboutScreen()}
        composable(route = Routes.NOTE_LIST) {NoteListScreen()}
        composable(route = Routes.SETTINGS) {SettingsScreen()}
    }
    
    
}