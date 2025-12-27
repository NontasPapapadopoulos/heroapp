package com.plum.superheroapp.domain.interactor

import com.plum.superheroapp.domain.repository.HeroRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class UpdateHeroTest {

    private lateinit var updateHero: UpdateHero

    @Mock
    private lateinit var heroRepository: HeroRepository

    private val dispatcher = UnconfinedTestDispatcher()


    @Before
    fun setUp() {
        updateHero = UpdateHero(heroRepository, dispatcher)
    }


    @Test
    fun execute_updateHero() = runTest {
        updateHero.execute(UpdateHero.Params(hero))
        verify(heroRepository).updateHero(hero)
    }


    companion object {
        val hero = DummyEntities.hero
    }
}