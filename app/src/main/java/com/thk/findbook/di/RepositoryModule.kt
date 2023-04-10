package com.thk.findbook.di

import com.thk.data.repository.RecentSearchesRepository
import com.thk.data.repository.RecentSearchesRepositoryImpl
import com.thk.data.repository.SearchRepository
import com.thk.data.repository.SearchRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindSearchRepository(searchRepositoryImpl: SearchRepositoryImpl): SearchRepository

    @Binds
    abstract fun bindRecentSearchesRepository(
        recentSearchesRepositoryImpl: RecentSearchesRepositoryImpl
    ): RecentSearchesRepository
}