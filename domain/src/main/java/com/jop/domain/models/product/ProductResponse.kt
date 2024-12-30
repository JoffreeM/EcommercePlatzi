package com.jop.domain.models.product

import com.jop.domain.entities.CartShoppingEntity
import com.jop.domain.models.category.CategoryResponse

data class ProductResponse(
    val id: Int,
    val title: String,
    val price: Int,
    val description: String,
    val images: List<String>,
    val creationAt: String,
    val updatedAt: String,
    val category: CategoryResponse
){
    fun onEntity(): CartShoppingEntity = CartShoppingEntity(
        id = id,
        nameProduct = title,
        price = price.toDouble(),
        image = images.first(),
        nameCategory = category.name
    )
}