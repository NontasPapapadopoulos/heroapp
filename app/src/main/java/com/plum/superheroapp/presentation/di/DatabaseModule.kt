package com.plum.superheroapp.presentation.di

import android.app.Application
import androidx.room.Room
import com.plum.superheroapp.data.cache.AppDatabase
import com.plum.superheroapp.data.cache.HeroDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {


    @Provides
    @Singleton
    fun providesDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            context = app,
            klass = AppDatabase::class.java,
            name = "hero_db"
        ).build()
    }


    @Provides
    @Singleton
    fun provideHeroDao(database: AppDatabase): HeroDao {
        return database.getHeroDao()
    }
}