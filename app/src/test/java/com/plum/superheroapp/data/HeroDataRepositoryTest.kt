package com.plum.superheroapp.data

import com.plum.superheroapp.data.datasource.HeroLocalDataSource
import com.plum.superheroapp.data.datasource.HeroRemoteDataSource
import com.plum.superheroapp.data.mapper.toData
import com.plum.superheroapp.data.mapper.toDomain
import com.plum.superheroapp.data.network.entity.HeroNetworkEntity
import com.plum.superheroapp.data.repository.HeroDataRepository
import com.plum.superheroapp.domain.interactor.DummyEntities
import com.plum.superheroapp.domain.interactor.hero
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class HeroDataRepositoryTest {
    private lateinit var heroDataRepository: HeroDataRepository

    @Mock
    private lateinit var localDataSource: HeroLocalDataSource

    @Mock
    private lateinit var remoteDataSource: HeroRemoteDataSource

    @Before
    fun setUp() {
        heroDataRepository = HeroDataRepository(localDataSource, remoteDataSource)
    }


    @Test
    fun fetchHeroesTest() = runTest {
        whenever(localDataSource.numberOfHeroes()).thenReturn(0)

        whenever(remoteDataSource.getHeroes(1))
            .thenReturn(heroesNetworkEntities)

        heroDataRepository.fetchHeroes(1)

        verify(localDataSource).addHeroes(heroesNetworkEntities.map { it.toData() })
    }

    @Test
    fun getHeroesTest() = runTest {
        whenever(localDataSource.getHeroes())
            .thenReturn(flowOf(heroDataEntities))

        val result = heroDataRepository.getHeroes().first()

        assertEquals(
            heroDataEntities.map { it.toDomain() },
            result
        )
    }


    @Test
    fun getSquadTest() = runTest {
        whenever(localDataSource.getSquad())
            .thenReturn(flowOf(heroDataEntities))

        val result = heroDataRepository.getSquad().first()

        assertEquals(
            heroDataEntities.map { it.toDomain() },
            result
        )

    }

    @Test
    fun getHeroTest() = runTest {
        whenever(localDataSource.getHero(any()))
            .thenReturn(flowOf(heroDataEntity))

        val result = heroDataRepository.getHero(1).first()

        assertEquals(
            heroDataEntity.toDomain() ,
            result
        )

    }

    @Test
    fun updateHeroTest() = runTest {
        val hero = DummyEntities.hero
        heroDataRepository.updateHero(hero)
        verify(localDataSource).updateHero(hero.toData())
    }


    companion object {
        val heroNetworkEntity = HeroNetworkEntity(
            _id = 0,
            name = "",
            url = "",
            imageUrl = "",
            videoGames = listOf(),
            enemies = listOf(),
            allies = listOf(),
            tvShows = listOf(),
            parkAttractions = listOf(),
            shortFilms = listOf(),
            films = listOf(),
        )
        val heroesNetworkEntities = listOf(heroNetworkEntity)


        val heroDataEntity = DummyEntities.hero.toData()
        val heroDataEntities = listOf(heroDataEntity)
    }
}