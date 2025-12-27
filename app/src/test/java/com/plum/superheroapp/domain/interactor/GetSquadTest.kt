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
class GetSquadTest {


    private lateinit var getSquad: GetSquad

    @Mock
    private lateinit var heroRepository: HeroRepository


    private val dispatcher = UnconfinedTestDispatcher()


    @Before
    fun setUp() {
        getSquad = GetSquad(heroRepository, dispatcher)
    }

    @Test
    fun execute_getSquad() = runTest {
        whenever(heroRepository.getSquad()).thenReturn(flowOf(squad))

        val result = getSquad.execute(Unit).first()

        assertEquals(
            Result.success(squad),
            result
        )
    }


    companion object {
        val squad = listOf(DummyEntities.hero)
    }

}