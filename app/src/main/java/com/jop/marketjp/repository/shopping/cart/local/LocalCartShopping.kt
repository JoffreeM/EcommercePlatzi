package com.jop.marketjp.repository.shopping.cart.local

import com.jop.domain.entities.CartShoppingEntity
import kotlinx.coroutines.flow.Flow

interface LocalCartShopping {
    suspend fun insertCartShopping(autoSave: CartShoppingEntity)
    suspend fun deleteCartById(id: Int)
    fun getAllCartShopping(): Flow<List<CartShoppingEntity>>
    fun getCountCartShopping(): Flow<Int>
}