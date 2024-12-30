package com.jop.marketjp.ui.screens.category.view.state

import com.jop.domain.models.product.ProductResponse
import com.jop.marketjp.ui.viewstate.ViewState

data class CategoryViewState(
    val isLoading: Boolean = false,
    val listProducts: List<ProductResponse> = emptyList(),
    val searchProduct: String = "",
    val priceMax: Int? = null,
    val priceMin: Int? = null,

    val nameCategory: String = "",
    val categoryImage: String = ""
): ViewState()
