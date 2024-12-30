package com.jop.marketjp.repository.shopping.cart.local

import com.jop.database.DBSuperStore
import com.jop.domain.entities.CartShoppingEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalCartShoppingImp @Inject constructor(
    private val dbSuperStore: DBSuperStore
): LocalCartShopping {
    override suspend fun insertCartShopping(autoSave: CartShoppingEntity) {
        dbSuperStore.cartShoppingDao().insertCartShopping(autoSave)
    }

    override suspend fun deleteCartById(id: Int) {
        dbSuperStore.cartShoppingDao().deleteCartById(id)
    }

    override fun getAllCartShopping(): Flow<List<CartShoppingEntity>> {
        return dbSuperStore.cartShoppingDao().getAllCartShopping()
    }

    override fun getCountCartShopping(): Flow<Int> {
        return dbSuperStore.cartShoppingDao().getCountCartShopping()
    }
}