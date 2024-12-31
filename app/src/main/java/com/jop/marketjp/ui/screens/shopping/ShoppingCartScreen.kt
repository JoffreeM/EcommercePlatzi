package com.jop.marketjp.ui.screens.shopping

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jop.domain.entities.CartShoppingEntity
import com.jop.marketjp.R
import com.jop.marketjp.ui.composables.CustomButton
import com.jop.marketjp.ui.composables.CustomIconButton
import com.jop.marketjp.ui.composables.CustomModalConfirmation
import com.jop.marketjp.ui.composables.CustomSpace
import com.jop.marketjp.ui.composables.CustomText
import com.jop.marketjp.ui.composables.CustomToolBar
import com.jop.marketjp.ui.composables.SimpleImage
import com.jop.marketjp.ui.screens.shopping.view.event.ShoppingCartViewEvent
import com.jop.marketjp.ui.screens.shopping.view.model.ShoppingCartViewModel
import com.jop.marketjp.ui.screens.shopping.view.state.ShoppingCartViewState

@Composable
fun ShoppingCartScreen(
    viewModel: ShoppingCartViewModel = hiltViewModel(),
    navController: NavController
) {
    val state by viewModel.getState<ShoppingCartViewState>().collectAsState()
    CustomToolBar(
        title = stringResource(id = R.string.cart_shopping_title),
        showReturnIcon = true,
        showIconCart = false,
        navController = navController
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(state.listShoppingCart) {
                ItemCartShopping(item = it, viewModel = viewModel)
            }
        }
        CustomSpace(height = 10)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row {
                CustomText(text = R.string.cart_shopping_subtitle_total, fontSize = 18)
                CustomSpace(width = 5)
                CustomText(
                    text = "$ ${state.listShoppingCart.sumOf { it.amount * it.price }}",
                    fontSize = 19,
                    fontWeight = FontWeight.Bold
                )
            }
            CustomButton(
                modifier = Modifier.width(150.dp),
                text = R.string.cart_shopping_subtitle_buy,
                icon = R.drawable.ic_money,
                onClick = {

                }
            )
        }
    }
}

@Composable
private fun ItemCartShopping(
    viewModel: ShoppingCartViewModel,
    item: CartShoppingEntity
) {
    val showModalDeleteCart = remember { mutableStateOf(false) }
    CustomModalConfirmation(
        showModal = showModalDeleteCart.value,
        title = stringResource(id = R.string.cart_shopping_modal_title),
        body = stringResource(id = R.string.cart_shopping_modal_body),
        onAccept = {
            viewModel.onEvent(ShoppingCartViewEvent.DeleteCartById(id = item.id))
        },
        onCancel = { showModalDeleteCart.value = false },
        onDismiss = { showModalDeleteCart.value = false }
    )
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                shape = RoundedCornerShape(10.dp)
            )
            .padding(5.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(15.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                contentAlignment = Alignment.TopStart
            ) {
                SimpleImage(
                    modifier = Modifier
                        .size(90.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .padding(2.dp),
                    imageUrl = item.image
                )
                IconButton(
                    modifier = Modifier.size(25.dp),
                    onClick = {
                        showModalDeleteCart.value = true
                    },
                    colors = IconButtonDefaults.iconButtonColors(Color.Red)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_close),
                        tint = Color.White,
                        contentDescription = null
                    )
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier,
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary),
                    elevation = CardDefaults.cardElevation(15.dp)
                ) {
                    CustomText(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        text = item.nameCategory,
                        fontSize = 16
                    )
                }
                CustomSpace(height = 6)
                Column {
                    Row {
                        CustomText(text = R.string.product_subtitle_price_uni, fontSize = 15)
                        CustomSpace(width = 5)
                        CustomText(
                            text = "$ ${item.price}",
                            fontSize = 16,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    CustomSpace(height = 6)
                    Row {
                        CustomText(text = R.string.product_subtitle_price_sub, fontSize = 15)
                        CustomSpace(width = 5)
                        CustomText(
                            text = "$ ${item.amount * item.price}",
                            fontSize = 16,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            NumberSpinner(
                value = item.amount,
                onValueChange = {
                    viewModel.onEvent(
                        ShoppingCartViewEvent.UpdateChangeAmount(
                            id = item.id,
                            newAmount = it
                        )
                    )
                }
            )
        }
    }
}

@Composable
fun NumberSpinner(
    value: Int,
    onValueChange: (Int) -> Unit,
    range: IntRange = 0..100
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(5.dp)
    ) {
        CustomIconButton(
            icon = R.drawable.ic_remove,
            enabled = value > range.first,
            onClick = {
                if (value > range.first) onValueChange(value - 1)
            }
        )
        CustomText(
            text = value.toString(),
            fontSize = 20,
            modifier = Modifier.padding(horizontal = 3.dp)
        )
        CustomIconButton(
            icon = R.drawable.ic_add,
            enabled = value < range.last,
            onClick = {
                if (value < range.last) onValueChange(value + 1)
            }
        )
    }
}
