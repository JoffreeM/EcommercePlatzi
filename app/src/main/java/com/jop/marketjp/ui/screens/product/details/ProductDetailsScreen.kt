package com.jop.marketjp.ui.screens.product.details

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jop.marketjp.R
import com.jop.marketjp.ui.composables.CustomButton
import com.jop.marketjp.ui.composables.CustomLoading
import com.jop.marketjp.ui.composables.CustomSpace
import com.jop.marketjp.ui.composables.CustomText
import com.jop.marketjp.ui.composables.CustomToolBar
import com.jop.marketjp.ui.composables.SimpleImage
import com.jop.marketjp.ui.screens.product.details.view.event.ProductDetailsViewEvent
import com.jop.marketjp.ui.screens.product.details.view.model.ProductDetailsViewModel
import com.jop.marketjp.ui.screens.product.details.view.state.ProductDetailsViewState
import com.jop.marketjp.ui.utils.cleanUrls
import kotlinx.coroutines.delay

@Composable
fun ProductDetailsScreen(
    viewModel: ProductDetailsViewModel = hiltViewModel(),
    navController: NavController
) {
    val state by viewModel.getState<ProductDetailsViewState>().collectAsState()
    CustomLoading(isLoading = state.isLoading)
    state.productDetail?.let { product ->
        CustomToolBar(
            title = "",
            navController = navController,
            showReturnIcon = true
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    AutoImageAnimation(listImage = product.images)
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        items(product.images) {
                            SimpleImage(
                                modifier = Modifier
                                    .size(100.dp)
                                    .padding(5.dp)
                                    .clip(RoundedCornerShape(15.dp)),
                                imageUrl = it
                            )
                        }
                    }
                }
                item {
                    CustomText(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        text = product.title,
                        fontSize = 21,
                        fontWeight = FontWeight.Bold
                    )
                    CustomSpace(height = 17)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row (
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            CustomText(text = R.string.product_subtitle_price, fontSize = 18)
                            CustomSpace(width = 5)
                            CustomText(
                                text = "$ ${product.price}",
                                fontSize = 22,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        CustomButton(
                            modifier = Modifier.width(160.dp),
                            text = R.string.product_subtitle_add_cart,
                            icon = R.drawable.ic_add_shopping_cart,
                            onClick = {
                                viewModel.onEvent(ProductDetailsViewEvent.AddCart(product))
                            }
                        )
                    }
                    CustomSpace(height = 17)
                    CustomText(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        text = R.string.product_subtitle_description,
                        fontSize = 17,
                        fontWeight = FontWeight.Bold
                    )
                    CustomSpace(height = 6)
                    CustomText(text = product.description)
                    CustomSpace(height = 10)
                }
            }
        }
    }


}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun AutoImageAnimation(
    modifier: Modifier = Modifier,
    listImage: List<String>
) {
    if (listImage.size <= 1) {
        SimpleImage(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .size(350.dp),
            imageUrl = listImage.first()
        )
    } else {
        var currentImage by remember { mutableIntStateOf(0) }
        LaunchedEffect(Unit) {
            while (true) {
                delay(3000) // Espera 3 segundos
                currentImage = (currentImage + 1) % listImage.size
            }
        }
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            AnimatedContent(
                targetState = currentImage,
                transitionSpec = {
                    fadeIn() with fadeOut()
                }, label = ""
            ) { targetImage ->
                // Verifica que el índice esté dentro de los límites antes de acceder
                val imageUrl = try {
                    val safeIndex = targetImage.coerceIn(0, listImage.cleanUrls().size - 1)
                    listImage.cleanUrls()[safeIndex]
                } catch (ex: Exception) {
                    ""
                }
                SimpleImage(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .size(350.dp),
                    imageUrl = imageUrl
                )
            }
        }
    }
}