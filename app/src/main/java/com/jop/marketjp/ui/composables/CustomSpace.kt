package com.jop.marketjp.ui.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CustomSpace(height:Int = 0,width:Int = 0){
    Spacer(
        modifier = Modifier
            .width(width.dp)
            .height(height.dp)
    )
}

@Composable
fun CustomSpace(size:Int = 0){
    Spacer(
        modifier = Modifier.size(size.dp)
    )
}