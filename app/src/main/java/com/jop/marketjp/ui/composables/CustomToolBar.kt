package com.jop.marketjp.ui.composables

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jop.marketjp.R
import com.jop.marketjp.ui.navigation.Screens
import com.jop.marketjp.ui.navigation.notification.NotificationViewModel
import com.jop.marketjp.ui.utils.snackbar.SnackbarAlign

@Composable
fun CustomToolBar(
    navController: NavController,
    title: String = "",
    showReturnIcon: Boolean = false,
    showIconCart: Boolean = true,
    showIcoLauncher: Boolean = false,
    @DrawableRes icon1: Int? = null,
    @DrawableRes icon2: Int? = null,
    snackbarAlign: SnackbarAlign = SnackbarAlign.BOTTOM_CENTER,
    onClickIcon1: () -> Unit = {},
    onClickIcon2: () -> Unit = {},
    viewModelNotification: NotificationViewModel = hiltViewModel(),
    content: @Composable () -> Unit = {}
) {
    val cartCountFlow by viewModelNotification.observeCountCart().collectAsState(initial = 0)
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        topBar = {
            TopBar(
                navController = navController,
                title = title,
                cartCountFlow = cartCountFlow,
                showReturnIcon = showReturnIcon,
                showIconCart = showIconCart,
                showIcoLauncher = showIcoLauncher,
                icon1 = icon1,
                icon2 = icon2,
                onClickIcon1 = onClickIcon1,
                onClickIcon2 = onClickIcon2
            )
        },
        snackbarHost = {
            CustomSnackbar(snackbarHostState = snackbarHostState, snackbarAlign = snackbarAlign)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it)
        ) {
            content()
        }
    }
}

@Composable
private fun TopBar(
    navController: NavController,
    title: String,
    showReturnIcon: Boolean,
    showIconCart: Boolean,
    showIcoLauncher: Boolean,
    cartCountFlow: Int,
    @DrawableRes icon1: Int?,
    @DrawableRes icon2: Int?,
    onClickIcon1: () -> Unit,
    onClickIcon2: () -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if(showIcoLauncher){
                Image(
                    modifier = Modifier.height(40.dp),
                    painter = painterResource(id = R.drawable.logo_top),
                    contentDescription = "Logo top"
                )
            }
            if (showReturnIcon){
                IconButton(
                    modifier = Modifier,
                    onClick = {
                        navController.popBackStack()
                    }
                ) {
                    Icon(
                        modifier = Modifier
                            .size(35.dp),
                        painter = painterResource(id = R.drawable.ic_back),
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = null,
                    )
                }
            }
            CustomSpace(width = 8)
            AnimatedVisibility(
                visible = title.isNotEmpty(),
                enter = fadeIn(animationSpec = tween(durationMillis = 1000)) +
                        slideInVertically(initialOffsetY = { it / 2 }, animationSpec = tween(1000)), // Ajusta la velocidad
                exit = fadeOut(animationSpec = tween(durationMillis = 1000)) +
                        slideOutVertically(targetOffsetY = { it / 2 }, animationSpec = tween(1000))
            ) {
                CustomText(
                    fontSize = 21,
                    fontWeight = FontWeight.SemiBold,
                    text = title
                )
            }
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            icon1?.let {
                IconButtonToolBar(icon = it, onClick = { onClickIcon1() })
            }
            icon2?.let {
                IconButtonToolBar(icon = it, onClick = { onClickIcon2() })
            }

            if (showIconCart) {
                BadgedBox(badge = {
                    Badge {
                        if (cartCountFlow>=1){
                            CustomText(text = "$cartCountFlow", fontSize = 11, textColor = Color.White)
                        }
                    }
                }) {
                    IconButtonToolBar(
                        icon = R.drawable.ic_shopping_cart,
                        onClick = { navController.navigate(Screens.SHOPPING_CART) }
                    )
                }
            }
        }
    }
}

@Composable
private fun IconButtonToolBar(
    @DrawableRes icon: Int,
    onClick: () -> Unit
) {
    IconButton(
        modifier = Modifier,
        onClick = {
            onClick()
        },
    ) {
        Icon(
            modifier = Modifier
                .size(36.dp),
            painter = painterResource(id = icon),
            tint = MaterialTheme.colorScheme.primary,
            contentDescription = null,
        )
    }
}