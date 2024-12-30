package com.jop.marketjp.ui.screens.home.tabs.product

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jop.domain.models.product.ProductResponse
import com.jop.marketjp.R
import com.jop.marketjp.ui.composables.CustomButton
import com.jop.marketjp.ui.composables.CustomSpace
import com.jop.marketjp.ui.composables.CustomText
import com.jop.marketjp.ui.composables.SimpleImage
import com.jop.marketjp.ui.navigation.Screens
import com.jop.marketjp.ui.screens.home.view.state.HomeViewState
import com.jop.marketjp.ui.utils.getRandomNumber
import kotlinx.coroutines.delay

@Composable
fun ProductTabView(
    state: HomeViewState,
    navController: NavController
) {
    LazyVerticalStaggeredGrid(
        modifier = Modifier.padding(5.dp),
        columns = StaggeredGridCells.Fixed(2)
    ) {
        items(state.listProducts) { item ->
            ItemProduct(item = item, navController = navController)
        }
    }
}

@Composable
private fun ItemProduct(
    item: ProductResponse,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .padding(3.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                shape = RoundedCornerShape(5.dp)
            ),
        onClick = {
            navController.navigate(Screens.PRODUCT_DETAILS)
        },
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        Column(
            modifier = Modifier.padding(5.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Row(Modifier.fillMaxWidth(), Arrangement.Center) {
                AutoImageAnimation(listImage = item.images)
            }
            CustomSpace(height = 3)
            CustomText(
                modifier = Modifier.padding(horizontal = 5.dp),
                text = item.title,
                fontSize = 14,
                fontWeight = FontWeight.Bold
            )
            CustomText(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 30.dp)
                    .padding(horizontal = 5.dp),
                text = item.description, fontSize = 13, overflow = TextOverflow.Ellipsis
            )
            CustomSpace(height = 8)
            Card(
                modifier = Modifier,
                shape = CircleShape,
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary),
                elevation = CardDefaults.cardElevation(15.dp)
            ) {
                CustomText(
                    modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp),
                    text = item.category.name,
                    fontSize = 13
                )
            }
            CustomSpace(height = 8)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    CustomText(text = "Precio", fontSize = 14)
                    CustomText(text = "$ ${item.price}", fontSize = 17)
                }
                IconButton(
                    onClick = { }
                ) {
                    Icon(
                        modifier = Modifier.size(40.dp),
                        painter = painterResource(id = R.drawable.ic_add_shopping_cart),
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = null
                    )
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
            SimpleImage(
                modifier = Modifier.clip(RoundedCornerShape(20.dp)).size(200.dp),
                imageUrl = listImage[targetImage]
            )
        }
    }
}