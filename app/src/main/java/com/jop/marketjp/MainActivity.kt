package com.jop.marketjp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.jop.marketjp.ui.navigation.Navigation
import com.jop.marketjp.ui.theme.MarketJPTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MarketJPTheme {
                val navController = rememberNavController()
                Navigation(navController = navController)
            }
        }
    }
}
