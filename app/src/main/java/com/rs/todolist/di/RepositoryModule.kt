package com.rs.todolist.di

import com.rs.todolist.data.datasource.local.AppDao
import com.rs.todolist.data.datasource.remote.RemoteDataSourceImpl
import com.rs.todolist.data.repository.AppRepositoryImpl
import com.rs.todolist.domain.repository.AppRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAppRepository(appDao: AppDao): AppRepository {
        val remoteDataSourceImpl = RemoteDataSourceImpl()
        return AppRepositoryImpl(remoteDataSourceImpl, appDao)
    }
}
