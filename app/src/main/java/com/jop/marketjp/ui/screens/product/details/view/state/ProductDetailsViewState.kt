package com.jop.marketjp.ui.screens.product.details.view.state

import com.jop.domain.models.product.ProductResponse
import com.jop.marketjp.ui.viewstate.ViewState

data class ProductDetailsViewState(
    val isLoading: Boolean = false,
    val productDetail: ProductResponse? = null
): ViewState()
