package com.jop.marketjp.ui.composables

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jop.domain.models.category.CategoryResponse
import com.jop.marketjp.R
import kotlinx.coroutines.launch

@Composable
fun ModalBottomSheet(
    sheetState: ModalBottomSheetState,
    categoryList: List<CategoryResponse> = emptyList(),
    settingView: SettingView = SettingView.ORDER,
    onClickOrder: (SortOption) -> Unit = {},
    content: @Composable () -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current
    BackHandler(enabled = sheetState.isVisible) {
        keyboardController?.hide()
        coroutineScope.launch {
            sheetState.hide()
        }
    }
    LaunchedEffect(key1 = !sheetState.isVisible){
        keyboardController?.hide()
        if (!sheetState.isVisible){

        }
    }
    ModalBottomSheetLayout(
        modifier = Modifier.fillMaxWidth(),
        sheetState = sheetState,
        sheetShape = RoundedCornerShape(
            topStart = 30.dp,
            topEnd = 30.dp,
            bottomStart = 0.dp,
            bottomEnd = 0.dp
        ),
        sheetBackgroundColor = MaterialTheme.colorScheme.surface,
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier
                        .size(30.dp)
                        .padding(bottom = 5.dp),
                    painter = painterResource(id = R.drawable.ic_bottom_sheet),
                    tint = MaterialTheme.colorScheme.secondary,
                    contentDescription = ""
                )
                when(settingView){
                    SettingView.FILTER -> {
                        FilterView(categoryList = categoryList, onClick = {})
                    }
                    SettingView.ORDER -> {
                        OrderView(sheetState = sheetState,onClick = onClickOrder)
                    }
                }
            }
        }
    ){
        content()
    }
}

@Composable
private fun FilterView(
    categoryList: List<CategoryResponse>,
    onClick: (CategoryResponse) -> Unit
){
    val showOptionCategory = remember { mutableStateOf(false) }
    val showOptionPrice = remember { mutableStateOf(false) }

    var selectedOption by remember { mutableStateOf<CategoryResponse?>(null) }
    Column(modifier = Modifier.padding(16.dp)) {
        CustomText(text = R.string.filter_title)
        CustomSpace(height = 10)
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            CustomText(text = R.string.filter_category_subtitle)
            CustomIconButton(
                icon = R.drawable.ic_arrow_down,
                onClick = {
                    showOptionCategory.value = !showOptionCategory.value
                }
            )
        }
        AnimatedVisibility(visible = showOptionCategory.value) {
            LazyColumn (
                modifier = Modifier.fillMaxWidth()
            ){
                items(categoryList){ category ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedOption = category
                                onClick(category) // Invoca la acción con la categoría seleccionada
                            }
                            .padding(vertical = 8.dp)
                    ) {
                        RadioButton(
                            selected = selectedOption == category,
                            onClick = {
                                selectedOption = category
                                onClick(category)
                            }
                        )
                        CustomText(
                            text = category.name,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        }
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            CustomText(text = R.string.filter_price_subtitle)
            CustomIconButton(
                icon = R.drawable.ic_arrow_down,
                onClick = {
                    showOptionPrice.value = !showOptionPrice.value
                }
            )
        }
        AnimatedVisibility(visible = showOptionPrice.value) {
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                CustomInput(
                    value = "",
                    onValueChange = {},
                    leadingIcon = {
                        Icon(painter = painterResource(id = R.drawable.ic_money), tint = MaterialTheme.colorScheme.primary, contentDescription = null)
                    }
                )
                CustomInput(
                    value = "",
                    onValueChange = {},
                    leadingIcon = {
                        Icon(painter = painterResource(id = R.drawable.ic_money), tint = MaterialTheme.colorScheme.primary, contentDescription = null)
                    }
                )
            }
        }
    }
}

@Composable
private fun OrderView(
    sheetState: ModalBottomSheetState,
    onClick: (SortOption) -> Unit
){
    val coroutineScope = rememberCoroutineScope()
    var selectedOption by remember { mutableStateOf(SortOption.ASCENDING) }
    Column(modifier = Modifier.padding(16.dp)) {
        CustomText(text = R.string.price_order_title)
        CustomSpace(height = 15)
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = selectedOption == SortOption.ASCENDING,
                onClick = { selectedOption = SortOption.ASCENDING }
            )
            CustomText(text = R.string.price_order_asc, modifier = Modifier.padding(start = 8.dp))
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = selectedOption == SortOption.DESCENDING,
                onClick = { selectedOption = SortOption.DESCENDING }
            )
            CustomText(text = R.string.price_order_desc, modifier = Modifier.padding(start = 8.dp))
        }
        CustomSpace(height = 15)
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ){
            CustomButton(
                modifier = Modifier.width(200.dp),
                text = R.string.price_order_apply,
                onClick = {
                    coroutineScope.launch {
                        sheetState.hide()
                        onClick(selectedOption)
                    }
                }
            )
        }
    }
}

enum class SettingView{
    FILTER,
    ORDER
}

enum class SortOption {
    ASCENDING,
    DESCENDING
}