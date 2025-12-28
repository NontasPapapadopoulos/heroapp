package com.plum.superheroapp.data.cache

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.plum.superheroapp.data.entity.HeroDataEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HeroDao {

    @Query("SELECT * FROM heroes")
    fun getHeroes(): Flow<List<HeroDataEntity>>

    @Query("SELECT * FROM heroes WHERE id = :id")
    fun getHero(id: Int): Flow<HeroDataEntity>

    @Query("SELECT * FROM heroes WHERE isSquadMember = 1")
    fun getSquadMembers(): Flow<List<HeroDataEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun put(heroes: List<HeroDataEntity>)

    @Query("UPDATE heroes SET isSquadMember = :isSquadMember WHERE id = :id")
    suspend fun updateHero(id: Int, isSquadMember: Boolean)

    @Query("SELECT COUNT(*) FROM heroes")
    suspend fun numberOfHeroes(): Int

}