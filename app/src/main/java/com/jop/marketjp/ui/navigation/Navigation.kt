package com.jop.marketjp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jop.marketjp.ui.screens.category.CategoryScreen
import com.jop.marketjp.ui.screens.home.HomeScreen
import com.jop.marketjp.ui.screens.product.details.ProductDetailsScreen
import com.jop.marketjp.ui.screens.shopping.ShoppingCartScreen
import com.jop.marketjp.ui.screens.splash.screen.SplashScreen

@Composable
fun Navigation(navController: NavController){
    NavHost(navController = navController as NavHostController,
        startDestination = Screens.HOME) {

        composable(Screens.SPLASH){
            SplashScreen(navController = navController)
        }
        composable(Screens.HOME){
            HomeScreen(navController = navController)
        }
        composable(Screens.CATEGORY){
            CategoryScreen(navController = navController)
        }
        composable(Screens.PRODUCT_DETAILS){
            ProductDetailsScreen(navController = navController)
        }
        composable(Screens.SHOPPING_CART){
            ShoppingCartScreen(navController = navController)
        }
    }
}