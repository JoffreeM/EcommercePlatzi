package com.jop.marketjp.ui.screens.category.view.event

interface CategoryViewEvent {
    data class UpdateSearchProduct(val value: String): CategoryViewEvent
    data class UpdatePriceMax(val value: Int?): CategoryViewEvent
    data class UpdatePriceMin(val value: Int?): CategoryViewEvent
    object SearchProduct: CategoryViewEvent
    object RefreshProduct: CategoryViewEvent
}