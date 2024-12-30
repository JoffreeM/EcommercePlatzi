package com.jop.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jop.domain.entities.CartShoppingEntity

@Database(
    entities = [
        CartShoppingEntity::class
    ], version = 1, exportSchema = false
)
abstract class DBSuperStore : RoomDatabase() {
    companion object {
        @JvmStatic
        fun newInstance(context: Context): DBSuperStore =
            Room.databaseBuilder(context, DBSuperStore::class.java, "DBSuperStore")
                .fallbackToDestructiveMigration()
                .build()
    }

}