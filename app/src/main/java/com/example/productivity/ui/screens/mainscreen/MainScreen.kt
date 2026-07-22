package com.example.productivity.ui.screens.mainscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.productivity.R
import com.example.productivity.dialog.MainDialog
import com.example.productivity.navigation.NavGraph
import com.example.productivity.utils.Routes
import com.example.productivity.utils.UiEvent

@Composable
fun MainScreen (
    mainNavController: NavHostController,
    viewModel: MainScreenViewModel = hiltViewModel()
){

    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val currentRoute = navBackStackEntry?.destination?.route

    val mainNavBackStackEntry by mainNavController.currentBackStackEntryAsState()

    val mainCurrentRoute = mainNavBackStackEntry?.destination?.route

    val isOnMainScreen = mainCurrentRoute == Routes.MAIN_SCREEN

    LaunchedEffect(currentRoute) {
        currentRoute?.let { route ->
            viewModel.updateFloatingButtonVisibility(route)
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { uiEvent ->
            when(uiEvent) {
                is UiEvent.Navigate -> {
                    navController.navigate(route = uiEvent.route)
                }
                is UiEvent.NavigateMain -> {
                    mainNavController.navigate(route = uiEvent.route)
                }
                else -> {}
            }

        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
        
    ){
        Scaffold(
            bottomBar =  {
                if (isOnMainScreen) {
                    BottomNav(
                        currentRoute = currentRoute
                    ) { route ->
                        navController.navigate(route = route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true // сохраняет состояния удалённых экранов
                            }
                            launchSingleTop = true // предотвращает дублирование экрана на верху стека
                            restoreState = true
                        }

                    }
                }
            }
        ) { paddingValues -> 
            Box(
                modifier = Modifier
                    .padding(paddingValues)
            ) {
                NavGraph(navController) { route ->
                    viewModel.onEvent(MainScreenEvent.NavigateMain(route))
                }
                MainDialog(viewModel)
            }
        }


        if (viewModel.showFloatingButton.value){
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(event = MainScreenEvent.OnNewItemClick(route = currentRoute ?: Routes.SHOPPING_LIST))
                },
                modifier = Modifier
                    .align(alignment = Alignment.BottomCenter)
                    .offset(y = (-60).dp)
                    .size(56.dp),
            ) {
                Icon(
                    painter = painterResource(R.drawable.add_ic),
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }



    }

}