package com.jop.marketjp.ui.screens.product.details.view.event

import com.jop.domain.models.product.ProductResponse

interface ProductDetailsViewEvent {
    data class AddCart(val value: ProductResponse): ProductDetailsViewEvent
}