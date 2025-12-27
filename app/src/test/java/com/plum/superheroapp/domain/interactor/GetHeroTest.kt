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
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class GetHeroTest {

    private lateinit var getHero: GetHero

    @Mock
    private lateinit var heroRepository: HeroRepository

    private val dispatcher = UnconfinedTestDispatcher()


    @Before
    fun setUp() {
        getHero = GetHero(heroRepository, dispatcher)
    }


    @Test
    fun execute_getHero() = runTest {
        whenever(heroRepository.getHero(any())).thenReturn(flowOf(hero))

        val result = getHero.execute(params = GetHero.Params(1)).first()

        assertEquals(
            Result.success(hero),
            result
        )
    }


    companion object {
        val hero = DummyEntities.hero
    }
}