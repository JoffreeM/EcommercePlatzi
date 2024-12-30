package com.jop.marketjp.ui.screens.home.data

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable

data class ItemTabPage(
    @StringRes val title: Int,
    val composable: @Composable () -> Unit
)