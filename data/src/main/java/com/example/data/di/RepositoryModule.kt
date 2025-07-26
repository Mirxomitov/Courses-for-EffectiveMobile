package com.example.data.di

import com.example.data.repository.AuthRepositoryImpl
import com.example.domain.repository.AuthRepository
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
    fun provideAuthRepository(
        // dependencies for AuthRepositoryImpl, e.g. ApiService
    ): AuthRepository {
        return AuthRepositoryImpl()
    }
}