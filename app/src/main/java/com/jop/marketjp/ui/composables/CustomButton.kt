package com.jop.marketjp.ui.composables

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jop.marketjp.ui.theme.ColorDegradedPrimary
import com.jop.marketjp.ui.utils.dynamicTextColor
import kotlinx.coroutines.delay


@Composable
fun CustomButton(
    modifier: Modifier,
    isEnabled: Boolean = true,
    fontSize: Int = 18,
    text: String,
    loadingAuto: Boolean = false,
    secondsLoadingAuto: Long = 3500,
    colorText: Color = Color.White,
    fontWeight: FontWeight = FontWeight.Bold,
    @DrawableRes icon: Int? = null,
    colorButtonSolid: Color? = null,
    colorButtonDegraded: List<Color> = ColorDegradedPrimary,
    onClick: () -> Unit
) {
    val isLoading = rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(key1 = isLoading.value) {
        if (isLoading.value){
            delay(secondsLoadingAuto)
            isLoading.value = false
        }
    }
    Card(
        onClick = {
            if (!isLoading.value) onClick()
            isLoading.value = loadingAuto
        },
        enabled = isEnabled,
        modifier = modifier
            .height(50.dp),
        colors = CardDefaults.cardColors(containerColor =
            colorButtonSolid ?: Color.Transparent
        ),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(if (colorButtonSolid == Color.Transparent) 0.dp else 5.dp)
    ) {
        Box(
            modifier =
            if (colorButtonSolid == null){
                Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = colorButtonDegraded,
                            startX = 980f,
                            endX = 1f
                        )
                    )
            } else {
                Modifier
                    .fillMaxSize()
                    .background(colorButtonSolid)
            }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(if (isEnabled) Color.Transparent else Color.Gray.copy(alpha = 0.4f)),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isLoading.value){
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ){
                        CustomCircularProgress(color = dynamicTextColor(colorButtonDegraded), modifier = Modifier.size(25.dp), isLoading = isLoading.value)
                    }
                } else {
                    if (icon != null) {
                        Image(
                            modifier = Modifier.padding(end = 6.dp),
                            painter = painterResource(id = icon),
                            alignment = Alignment.Center,
                            contentDescription = null
                        )
                    }
                    Text(
                        text = text,
                        color = if (colorButtonSolid == Color.Transparent) MaterialTheme.colorScheme.primary else colorText,
                        fontSize = fontSize.sp,
                        fontWeight = fontWeight,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}


@Composable
fun CustomButton(
    modifier: Modifier,
    @StringRes text: Int,
    fontSize: Int = 18,
    isEnabled: Boolean = true,
    loadingAuto: Boolean = false,
    secondsLoadingAuto: Long = 3500,
    colorText: Color = Color.White,
    fontWeight: FontWeight = FontWeight.Bold,
    @DrawableRes icon: Int? = null,
    colorButtonSolid: Color? = null,
    colorButtonDegraded: List<Color> = ColorDegradedPrimary,
    onClick: () -> Unit
) {
    val isLoading = rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(key1 = isLoading.value) {
        if (isLoading.value){
            delay(secondsLoadingAuto)
            isLoading.value = false
        }
    }
    Card(
        onClick = {
            if (!isLoading.value) onClick()
            isLoading.value = loadingAuto
        },
        enabled = isEnabled,
        modifier = modifier
            .height(50.dp),
        colors = CardDefaults.cardColors(containerColor =
        colorButtonSolid ?: Color.Transparent
        ),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(if (colorButtonSolid == Color.Transparent) 0.dp else 5.dp)
    ) {
        Box(
            modifier =
            if (colorButtonSolid == null){
                Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = colorButtonDegraded,
                            startX = 980f,
                            endX = 1f
                        )
                    )
            } else {
                Modifier
                    .fillMaxSize()
                    .background(colorButtonSolid)
            }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(if (isEnabled) Color.Transparent else Color.Gray.copy(alpha = 0.4f)),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isLoading.value){
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ){
                        CustomCircularProgress(modifier = Modifier.size(25.dp), isLoading = isLoading.value)
                    }
                } else {
                    if (icon != null) {
                        Image(
                            modifier = Modifier.padding(end = 6.dp),
                            painter = painterResource(id = icon),
                            alignment = Alignment.Center,
                            contentDescription = null
                        )
                    }
                    Text(
                        text = stringResource(id = text),
                        color = if (colorButtonSolid == Color.Transparent) MaterialTheme.colorScheme.primary else colorText,
                        fontSize = fontSize.sp,
                        fontWeight = fontWeight,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

