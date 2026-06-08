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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.productivity.R
import com.example.productivity.navigation.NavGraph

@Composable
fun MainScreen (){
    val navController = rememberNavController()

    Box(
        modifier = Modifier
            .fillMaxSize()
        
    ){
        Scaffold(
            bottomBar =  {BottomNav(navController)},
            topBar = {},
            
        ) { paddingValues -> 
            Box(
                modifier = Modifier
                    .padding(paddingValues)
            )
        }

        FloatingActionButton(
            onClick = {},
            modifier = Modifier
                .align(alignment = Alignment.BottomCenter)
                .offset(y = (-40).dp)
                .size(56.dp),
        ) {
            Icon(
                painter = painterResource(R.drawable.add_ic),
                contentDescription = null,
                tint = Color.White
            )
        }
        
        NavGraph(navController)
        
        
    }
    
    
    
}