package com.example.courses.di

import android.content.Context
import com.example.courses.CoursesApp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContext(app: CoursesApp): Context {
        return app.applicationContext
    }
}
