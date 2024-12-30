package com.jop.marketjp.ui.screens.home.view.event

interface HomeViewEvent {
    data class UpdateSearchProduct(val value: String): HomeViewEvent
    object SearchProduct: HomeViewEvent
}