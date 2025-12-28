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
class FetchHeroesTest {

    private lateinit var fetchHeroes: FetchHeroes

    @Mock
    private lateinit var heroRepository: HeroRepository

    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        fetchHeroes = FetchHeroes(heroRepository, dispatcher)
    }

    @Test
    fun execute_fetchHeroes() = runTest {
        fetchHeroes.execute(FetchHeroes.Params(2))
        verify(heroRepository).fetchHeroes(2)
    }
}