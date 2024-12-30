package com.jop.marketjp.ui.screens.home.view.state

import com.jop.domain.models.category.CategoryResponse
import com.jop.domain.models.product.ProductResponse
import com.jop.marketjp.ui.viewstate.ViewState

data class HomeViewState(
    val isLoading: Boolean = false,
    val listCategory: List<CategoryResponse> = emptyList(),
    val listProducts: List<ProductResponse> = emptyList(),
    val searchProduct: String = ""
): ViewState()
