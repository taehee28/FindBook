package com.thk.findbook.di

import com.thk.data.remote.ApiService
import com.thk.data.remote.BookApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideBookApiInterface(): BookApiInterface =
        ApiService.create(BookApiInterface::class.java)
}