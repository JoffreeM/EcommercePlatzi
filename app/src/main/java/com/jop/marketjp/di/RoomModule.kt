package com.jop.marketjp.di

import android.content.Context
import com.jop.database.DBSuperStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {
    @Singleton
    @Provides
    fun provideRoomDB(
        @ApplicationContext
        context: Context
    ): DBSuperStore = DBSuperStore.newInstance(context)
}