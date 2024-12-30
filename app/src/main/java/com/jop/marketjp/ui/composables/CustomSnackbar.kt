package com.jop.marketjp.ui.composables

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jop.marketjp.App
import com.jop.marketjp.ui.navigation.Params
import com.jop.marketjp.ui.theme.ColorGrayGentle
import com.jop.marketjp.ui.utils.snackbar.IcoSnackbar
import com.jop.marketjp.ui.utils.snackbar.SnackbarAlign
import com.jop.marketjp.ui.utils.snackbar.SnackbarManager

@Composable
fun CustomSnackbar(
    snackbarHostState: SnackbarHostState,
    snackbarAlign: SnackbarAlign
) {
    var isVisible by remember { mutableStateOf(false) }

    val rotation by animateFloatAsState(
        targetValue = if (isVisible) 360f else 0f,
        animationSpec = tween(durationMillis = 900), label = ""
    )
    val scale by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(durationMillis = 900), label = ""
    )
    LaunchedEffect(key1 = snackbarHostState.currentSnackbarData) {
        isVisible = snackbarHostState.currentSnackbarData != null
    }
    LaunchedEffect(Unit) {
        SnackbarManager.snackbarEvents.collect { message ->
            snackbarHostState.showSnackbar(message, withDismissAction = true)
        }
    }
    Box (
        modifier = Modifier.fillMaxSize()
    ){
        val modifier = when(snackbarAlign){
            SnackbarAlign.TOP_CENTER -> Modifier.padding(30.dp).align(Alignment.TopCenter)
            SnackbarAlign.CENTER -> Modifier.padding(30.dp).align(Alignment.Center)
            SnackbarAlign.BOTTOM_CENTER -> Modifier.padding(30.dp).align(Alignment.BottomCenter)
        }
        SnackbarHost(
            modifier = modifier,//Modifier.padding(30.dp).align(Alignment.TopCenter),
            hostState = snackbarHostState,
            snackbar = { snackbarData ->
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(ColorGrayGentle),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 15.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        val icoSnackbar = App.navigation[Params.ICO_SNACKBAR]
                        if (icoSnackbar is IcoSnackbar){
                            Image(
                                modifier = Modifier
                                    .size(25.dp)
                                    .rotate(rotation)
                                    .scale(scale),
                                painter = painterResource(id = SnackbarManager.getIco(icoSnackbar)),
                                contentDescription = ""
                            )
                        }
                        CustomText(
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 3.dp),
                            textAlign = TextAlign.Center,
                            textColor = Color.Gray,
                            text = snackbarData.visuals.message
                        )
                        TextButton(
                            onClick = { snackbarHostState.currentSnackbarData?.dismiss() }
                        ) {
                            CustomText(
                                modifier = Modifier.width(40.dp),
                                fontSize = 14,
                                textColor = Color.Gray,
                                fontWeight = FontWeight.SemiBold,
                                text = "Cerrar"
                            )
                        }
                    }
                }
            }
        )
    }
}