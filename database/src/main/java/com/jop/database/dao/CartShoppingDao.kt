package com.jop.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jop.domain.entities.CartShoppingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartShoppingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartShopping(autoSave: CartShoppingEntity)

    @Query("DELETE FROM cart_shopping WHERE id = :id")
    suspend fun deleteCartById(id: Int)

    @Query("SELECT * FROM cart_shopping")
    fun getAllCartShopping(): Flow<List<CartShoppingEntity>>

    @Query("SELECT COUNT(*) FROM cart_shopping")
    fun getCountCartShopping(): Flow<Int>
}