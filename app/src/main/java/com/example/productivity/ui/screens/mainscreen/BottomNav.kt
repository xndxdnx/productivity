package com.example.productivity.ui.screens.mainscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.productivity.ui.theme.BlueLight
import com.example.productivity.ui.theme.GrayLight



@Composable
fun BottomNav (
    navHostController: NavHostController
) {
   
    val bottomItems = listOf(
        BottomNavItem.AboutItem,
        BottomNavItem.SettingsItem,
        BottomNavItem.ShoppingListItem,
        BottomNavItem.NoteListItem
    )

    NavigationBar(
        modifier = Modifier
            .background(color = Color.White)
            .height(70.dp),
    ) {
        bottomItems.forEach { bottomNavItem -> 
            val navBackStackEntry by navHostController.currentBackStackEntryAsState()
            
            val currentRoute = navBackStackEntry?.destination?.route

            NavigationBarItem(
                selected = currentRoute == bottomNavItem.route,
                onClick = {
                    navHostController.navigate(route = bottomNavItem.route)
                },
                icon = {
                    Icon(
                        painter = painterResource(bottomNavItem.icon),
                        contentDescription = null
                    )
                },
                label = {
                    Text(
                        text = bottomNavItem.title,
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = BlueLight,
                    unselectedIconColor = GrayLight,
                    selectedTextColor = BlueLight,
                    unselectedTextColor = GrayLight,
                ),
                alwaysShowLabel = false
            ) 
        }
    }
    
}


