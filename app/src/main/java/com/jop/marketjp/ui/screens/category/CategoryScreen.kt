package com.jop.marketjp.ui.screens.category

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.jop.marketjp.ui.composables.CustomToolBar

@Composable
fun CategoryScreen(
    navController: NavController
){
    CustomToolBar(
        title = "",
        navController = navController
    )
}