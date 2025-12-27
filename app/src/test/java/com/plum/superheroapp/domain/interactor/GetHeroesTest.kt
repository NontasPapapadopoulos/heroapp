package com.plum.superheroapp.domain.interactor

import com.plum.superheroapp.domain.repository.HeroRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class GetHeroesTest {

    private lateinit var getHeroes: GetHeroes


    @Mock
    private lateinit var heroRepository: HeroRepository

    private val dispatcher = UnconfinedTestDispatcher()


    @Before
    fun setUp() {
        getHeroes = GetHeroes(heroRepository, dispatcher)
    }


    @Test
    fun execute_getHeroes() = runTest {
        whenever(heroRepository.getHeroes()).thenReturn(flowOf(heroes))

        val result = getHeroes.execute(Unit).first()

        assertEquals(
            Result.success(heroes),
            result
        )
    }


    companion object {
        val heroes = listOf(DummyEntities.hero)
    }
}