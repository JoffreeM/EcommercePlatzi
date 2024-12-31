package com.jop.marketjp.ui.screens.category

import androidx.annotation.StringRes
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jop.marketjp.R
import com.jop.marketjp.ui.composables.CustomIconButton
import com.jop.marketjp.ui.composables.CustomInput
import com.jop.marketjp.ui.composables.CustomLoading
import com.jop.marketjp.ui.composables.CustomText
import com.jop.marketjp.ui.composables.CustomToolBar
import com.jop.marketjp.ui.composables.ModalBottomSheet
import com.jop.marketjp.ui.composables.SettingView
import com.jop.marketjp.ui.composables.SimpleImage
import com.jop.marketjp.ui.composables.SortOption
import com.jop.marketjp.ui.screens.category.view.event.CategoryViewEvent
import com.jop.marketjp.ui.screens.category.view.model.CategoryViewModel
import com.jop.marketjp.ui.screens.category.view.state.CategoryViewState
import com.jop.marketjp.ui.screens.home.tabs.product.ItemProduct
import kotlinx.coroutines.launch

@Composable
fun CategoryScreen(
    viewModel: CategoryViewModel = hiltViewModel(),
    navController: NavController
) {
    val state by viewModel.getState<CategoryViewState>().collectAsState()
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    var orderOption by remember { mutableStateOf(SortOption.ASCENDING) }
    var optionSheet by remember { mutableStateOf(SettingView.ORDER) }
    val coroutineScope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current
    CustomLoading(isLoading = state.isLoading)
    ModalBottomSheet(
        settingView = optionSheet,
        categoryList = emptyList(),
        showFilterCategory = false,
        sheetState = sheetState,
        onClickOrder = { orderOption = it },
        onClickFilter = {
            viewModel.onEvent(CategoryViewEvent.UpdatePriceMax(it.priceMax))
            viewModel.onEvent(CategoryViewEvent.UpdatePriceMin(it.priceMin))
            viewModel.onEvent(CategoryViewEvent.SearchProduct)
        }
    ) {
        CustomToolBar(
            title = state.nameCategory,
            showReturnIcon = true,
            navController = navController
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                item {
                    CardCategoryView(
                        urlImage = state.categoryImage,
                        nameCategory = state.nameCategory
                    )
                }
                item {
                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        CustomInput(
                            modifier = Modifier,
                            placeholder = R.string.home_tab_product_input_search,
                            value = state.searchProduct,
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Search,
                                keyboardType = KeyboardType.Text
                            ),
                            keyboardActions = KeyboardActions(
                                onSearch = {
                                    keyboardController?.hide()
                                    viewModel.onEvent(CategoryViewEvent.SearchProduct)
                                }
                            ),
                            trailingIcon = {
                                CustomIconButton(
                                    icon = R.drawable.ic_search,
                                    onClick = {
                                        keyboardController?.hide()
                                        viewModel.onEvent(CategoryViewEvent.SearchProduct)
                                    }
                                )
                            },
                            onValueChange = {
                                viewModel.onEvent(CategoryViewEvent.UpdateSearchProduct(it))
                            }
                        )
                        CustomIconButton(
                            icon = R.drawable.ic_close,
                            sizeIcon = 40.dp,
                            onClick = {
                                keyboardController?.hide()
                                viewModel.onEvent(CategoryViewEvent.RefreshProduct)
                            }
                        )
                    }
                }
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        ButtonFilter(
                            name = R.string.category_order_button,
                            onClick = {
                                coroutineScope.launch { sheetState.show() }
                                optionSheet = SettingView.ORDER
                            }
                        )
                        ButtonFilter(
                            name = R.string.category_filter_button,
                            onClick = {
                                coroutineScope.launch { sheetState.show() }
                                optionSheet = SettingView.FILTER
                            }
                        )
                    }
                }
                val sortedList = when (orderOption) {
                    SortOption.ASCENDING -> state.listProducts.sortedBy { it.price }
                    SortOption.DESCENDING -> state.listProducts.sortedByDescending { it.price }
                }
                items(sortedList) { item ->
                    ItemProduct(
                        modifier = Modifier.padding(10.dp),
                        item = item,
                        navController = navController,
                        onClickAddCart = {
                            viewModel.onEvent(CategoryViewEvent.AddCartShopping(item))
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun CardCategoryView(
    urlImage: String,
    nameCategory: String
) {
    Card(
        modifier = Modifier.padding(10.dp),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        SimpleImage(
            modifier = Modifier
                .fillMaxWidth()
                .size(300.dp),
            imageUrl = urlImage
        )
        Card(
            modifier = Modifier.padding(start = 16.dp, bottom = 16.dp),
            shape = CircleShape,
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary),
            elevation = CardDefaults.cardElevation(15.dp)
        ) {
            CustomText(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                text = nameCategory,
                fontSize = 18
            )
        }
    }
}

@Composable
private fun ButtonFilter(
    @StringRes name: Int,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .padding(start = 16.dp, bottom = 16.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                shape = RoundedCornerShape(15.dp)
            ),
        shape = CircleShape,
        onClick = onClick,
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(15.dp)
    ) {
        CustomText(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            text = name,
            fontSize = 18
        )
    }
}