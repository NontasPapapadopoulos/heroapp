package com.plum.superheroapp.data.repository

import com.plum.superheroapp.data.datasource.HeroLocalDataSource
import com.plum.superheroapp.data.datasource.HeroRemoteDataSource
import com.plum.superheroapp.data.entity.HeroDataEntity
import com.plum.superheroapp.data.mapper.toData
import com.plum.superheroapp.data.mapper.toDomain
import com.plum.superheroapp.domain.entity.HeroDomainEntity
import com.plum.superheroapp.domain.repository.HeroRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.collections.map

class HeroDataRepository @Inject constructor(
    private val localDataSource: HeroLocalDataSource,
    private val remoteDataSource: HeroRemoteDataSource
): HeroRepository {

    override suspend fun fetchHeroes(page: Int) {
        val numberOfHeroes = localDataSource.numberOfHeroes()
        val shouldFetchHeroes = page > numberOfHeroes / 50

        if (shouldFetchHeroes) {
            val heroes = remoteDataSource.getHeroes(page)
                .map { it.toData() }
            localDataSource.addHeroes(heroes)
        }

    }

    override fun getHeroes(): Flow<List<HeroDomainEntity>> {
        return localDataSource.getHeroes()
            .map { heroes ->
                heroes.map {
                    it.toDomain()
                }
            }
    }

    override fun getSquad(): Flow<List<HeroDomainEntity>> {
        return localDataSource.getSquad()
            .map { heroes ->
                heroes.map {
                    it.toDomain()
                }
            }
    }

    override fun getHero(id: Int): Flow<HeroDomainEntity> {
        return localDataSource.getHero(id)
            .map { it.toDomain() }
    }

    override suspend fun updateHero(hero: HeroDomainEntity) {
        localDataSource.updateHero(hero.toData())
    }

}