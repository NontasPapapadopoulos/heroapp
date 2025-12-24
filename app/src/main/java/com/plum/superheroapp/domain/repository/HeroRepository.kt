package com.plum.superheroapp.domain.repository

import com.plum.superheroapp.domain.entity.HeroDomainEntity
import kotlinx.coroutines.flow.Flow

interface HeroRepository {
    suspend fun fetchHeroes()
    fun getHeroes(): Flow<List<HeroDomainEntity>>
    fun getSquad(): Flow<List<HeroDomainEntity>>
    fun getHero(id: Int): Flow<HeroDomainEntity>
    suspend fun updateHero(hero: HeroDomainEntity)
}