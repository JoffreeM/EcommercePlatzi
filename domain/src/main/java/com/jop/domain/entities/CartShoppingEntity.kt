package com.jop.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_shopping")
data class CartShoppingEntity(
    @PrimaryKey
    val id: Int,
    val nameProduct: String,
    val amount: Int = 1,
    val price: Double,
    val image: String,
    val nameCategory: String
)
