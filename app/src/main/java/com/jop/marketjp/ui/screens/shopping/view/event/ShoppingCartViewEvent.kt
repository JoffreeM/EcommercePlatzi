package com.jop.marketjp.ui.screens.shopping.view.event

interface ShoppingCartViewEvent {
    data class UpdateChangeAmount(val id: Int, val newAmount: Int): ShoppingCartViewEvent
    data class DeleteCartById(val id: Int): ShoppingCartViewEvent
}