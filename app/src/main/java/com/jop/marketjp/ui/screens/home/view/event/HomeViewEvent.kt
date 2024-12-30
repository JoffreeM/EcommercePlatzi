package com.jop.marketjp.ui.screens.home.view.event

interface HomeViewEvent {
    data class UpdateSearchProduct(val value: String): HomeViewEvent
    data class UpdateCategoryId(val value: Int?): HomeViewEvent
    data class UpdatePriceMax(val value: Int?): HomeViewEvent
    data class UpdatePriceMin(val value: Int?): HomeViewEvent
    object SearchProduct: HomeViewEvent
}