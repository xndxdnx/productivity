package com.example.productivity.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.productivity.ui.screens.additemscreen.AddItemScreen
import com.example.productivity.ui.screens.mainscreen.MainScreen
import com.example.productivity.ui.screens.newnotescreen.NewNoteScreen
import com.example.productivity.utils.Routes

@Composable
fun MainNavigationGraph (

) {
    val navController = rememberNavController()

    NavHost(
        navController =  navController,
        startDestination = Routes.MAIN_SCREEN
    ){
        composable (Routes.MAIN_SCREEN) { MainScreen(navController) }
        composable (Routes.ADD_ITEM + "/{listId}") { AddItemScreen ()  }
        composable (Routes.NEW_NOTE + "/{noteId}") { NewNoteScreen () }
    }
}