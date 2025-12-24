package com.plum.superheroapp.presentation.di

import com.plum.superheroapp.data.datasource.HeroLocalDataSource
import com.plum.superheroapp.data.datasource.HeroRemoteDataSource
import com.plum.superheroapp.data.repository.HeroDataRepository
import com.plum.superheroapp.domain.repository.HeroRepository
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
    fun provideHeroRepository(
        heroLocalDataSource: HeroLocalDataSource,
        heroRemoteDataSource: HeroRemoteDataSource
    ): HeroRepository {
        return HeroDataRepository(
            heroLocalDataSource,
            heroRemoteDataSource
        )
    }

}