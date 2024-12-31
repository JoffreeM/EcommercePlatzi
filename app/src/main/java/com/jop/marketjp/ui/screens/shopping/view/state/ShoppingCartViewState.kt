package com.jop.marketjp.ui.screens.shopping.view.state

import com.jop.domain.entities.CartShoppingEntity
import com.jop.marketjp.ui.viewstate.ViewState

data class ShoppingCartViewState(
    val isLoading: Boolean = false,
    val listShoppingCart: List<CartShoppingEntity> = emptyList()
): ViewState()
