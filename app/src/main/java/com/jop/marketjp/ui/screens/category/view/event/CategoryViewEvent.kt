package com.jop.marketjp.ui.screens.category.view.event

import com.jop.domain.models.product.ProductResponse

interface CategoryViewEvent {
    data class UpdateSearchProduct(val value: String): CategoryViewEvent
    data class UpdatePriceMax(val value: Int?): CategoryViewEvent
    data class UpdatePriceMin(val value: Int?): CategoryViewEvent
    data class AddCartShopping(val value: ProductResponse): CategoryViewEvent
    object SearchProduct: CategoryViewEvent
    object RefreshProduct: CategoryViewEvent
}