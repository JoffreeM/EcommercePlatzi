package com.jop.marketjp.ui.composables

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.jop.marketjp.R


@Composable
fun SimpleImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    @DrawableRes placeholder: Int = R.drawable.ic_image,
    colorImage: Color? = null,
    contentScale: ContentScale = ContentScale.Crop
) {
    var isLoading by remember { mutableStateOf(true) }
    var size by remember { mutableStateOf(IntSize.Zero) }
    val tintColor = if (colorImage == null) null else ColorFilter.tint(colorImage)

    Box (
        modifier = modifier.onGloballyPositioned { coordinates ->
            size = coordinates.size
        },
        contentAlignment = Alignment.Center
    ){
        AsyncImage(
            modifier = modifier,
            model = imageUrl.takeIf { it.isNotBlank() } ?: placeholder,
            onLoading = {
                isLoading = true
            },
            onSuccess = {
                isLoading = false
            },
            onError = {
                isLoading = false
            },
            placeholder = painterResource(id = placeholder),
            error = painterResource(id = R.drawable.ic_warning),
            contentScale = contentScale,
            colorFilter = tintColor,
            contentDescription = "",
        )
        if (isLoading && size.width > 2){
            CircularProgressIndicator(
                modifier = modifier.padding(((size.width/7)).dp)
            )
        }
    }
}

@Composable
fun SimpleImage(
    modifier: Modifier = Modifier,
    @DrawableRes imageUrl: Int,
    @DrawableRes placeholder: Int = R.drawable.ic_image,
    colorImage: Color? = null,
    contentScale: ContentScale = ContentScale.Crop
) {
    var isLoading by remember { mutableStateOf(true) }
    var size by remember { mutableStateOf(IntSize.Zero) }
    val tintColor = if (colorImage == null) null else ColorFilter.tint(colorImage)
    Box (
        modifier = modifier.onGloballyPositioned { coordinates ->
            size = coordinates.size
        },
        contentAlignment = Alignment.Center
    ){
        AsyncImage(
            modifier = modifier,
            model = imageUrl,
            onLoading = {
                isLoading = true
            },
            onSuccess = {
                isLoading = false
            },
            onError = {
                isLoading = false
            },
            placeholder = painterResource(id = placeholder),
            error = painterResource(id = R.drawable.ic_warning),
            contentScale = contentScale,
            colorFilter = tintColor,
            contentDescription = "",
        )
        if (isLoading && size.width > 2){
            CircularProgressIndicator(
                modifier = modifier.padding(((size.width/7)).dp)
            )
        }
    }
}

