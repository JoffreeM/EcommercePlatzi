package com.jop.marketjp.ui.screens.home.tabs.product

import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ProductTabView(
    navController: NavController
){
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(128.dp)
    ) {
        
    }
}

private fun ItemProduct(

){

}