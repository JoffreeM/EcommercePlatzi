package com.jop.marketjp.ui.composables


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CustomCircularProgress(
    modifier: Modifier = Modifier,
    sizeProgress: Dp = 48.dp,
    isLoading: Boolean = false,
    color: Color = MaterialTheme.colorScheme.primary
){
    if (isLoading){
        Row (
            modifier = modifier,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            CircularProgressIndicator(
                modifier = Modifier.size(sizeProgress),
                color = color
            )
        }
    }
}