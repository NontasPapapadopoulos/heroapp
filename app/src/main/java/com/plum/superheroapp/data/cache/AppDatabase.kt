package com.plum.superheroapp.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.plum.superheroapp.data.entity.HeroDataEntity
import com.plum.superheroapp.data.util.Converters

@Database(
    entities = [
        HeroDataEntity::class
    ],
    exportSchema = false,
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase(): RoomDatabase() {
    abstract fun getHeroDao(): HeroDao

}