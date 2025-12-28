package com.plum.superheroapp.data.datasource

import com.plum.superheroapp.data.cache.HeroDao
import com.plum.superheroapp.data.entity.HeroDataEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import javax.inject.Inject

interface HeroLocalDataSource {
    fun getHeroes(): Flow<List<HeroDataEntity>>
    fun getSquad(): Flow<List<HeroDataEntity>>
    fun getHero(id: Int): Flow<HeroDataEntity>
    suspend fun updateHero(hero: HeroDataEntity)
    suspend fun addHeroes(heroes: List<HeroDataEntity>)

    suspend fun numberOfHeroes(): Int
}


class HeroLocalDataSourceImpl @Inject constructor(
    private val heroDao: HeroDao
): HeroLocalDataSource {

    override fun getHeroes(): Flow<List<HeroDataEntity>> {
        return heroDao.getHeroes()
            .filterNotNull()
    }

    override fun getSquad(): Flow<List<HeroDataEntity>> {
        return heroDao.getSquadMembers()
            .filterNotNull()
    }

    override fun getHero(id: Int): Flow<HeroDataEntity> {
        return heroDao.getHero(id)
            .filterNotNull()
    }

    override suspend fun updateHero(hero: HeroDataEntity) {
        heroDao.updateHero(hero.id, !hero.isSquadMember )
    }

    override suspend fun addHeroes(heroes: List<HeroDataEntity>) {
        heroDao.put(heroes)
    }

    override suspend fun numberOfHeroes(): Int {
        return heroDao.numberOfHeroes()
    }


}