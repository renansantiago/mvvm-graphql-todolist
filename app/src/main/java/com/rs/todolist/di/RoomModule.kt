package com.rs.todolist.di

import android.content.Context
import com.rs.todolist.data.datasource.local.AppDb
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
    fun provideAppDatabase(@ApplicationContext context: Context) = AppDb.getDatabase(context)

    @Provides
    fun provideAppDAO(db: AppDb) = db.appDao()
}
