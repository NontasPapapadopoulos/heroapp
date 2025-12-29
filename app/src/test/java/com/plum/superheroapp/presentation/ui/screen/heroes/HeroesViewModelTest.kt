package com.plum.superheroapp.presentation.ui.screen.heroes

import com.plum.superheroapp.domain.interactor.DummyEntities
import com.plum.superheroapp.domain.interactor.FetchHeroes
import com.plum.superheroapp.domain.interactor.GetHeroes
import com.plum.superheroapp.domain.interactor.GetSquad
import com.plum.superheroapp.domain.interactor.hero
import com.plum.superheroapp.presentation.MainDispatcherRule
import com.plum.superheroapp.presentation.onEvents
import junit.framework.TestCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.whenever
import java.net.UnknownHostException

@RunWith(MockitoJUnitRunner::class)
class HeroesViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: HeroesViewModel

    @Mock
    private lateinit var getHeroes: GetHeroes

    @Mock
    private lateinit var getSquad: GetSquad

    @Mock
    private lateinit var fetchHeroes: FetchHeroes

    @Before
    fun setUp() = runTest {


        whenever(getSquad.execute(Unit))
            .thenReturn(flowOf(Result.success(squad)))
    }



    @Test
    fun onFlowStart_returnsHeroesAndSquad() = runTest {
        whenever(getHeroes.execute(Unit))
            .thenReturn(flowOf(Result.success(heroes)))

        whenever(getSquad.execute(Unit))
            .thenReturn(flowOf(Result.success(squad)))

        initViewModel()

        onEvents(
            viewModel
        ) { collectedStates ->
            assertEquals(
                listOf(
                    HeroesState.Loading,
                    defaultContent,
                    defaultContent.copy(
                        heroes = heroes.toHeroItems(),
                        squad = heroes.toHeroItems()
                    )
                ),
                collectedStates
            )

        }
    }

    @Test
    fun onFlowStart_throwsExceptionAndShowsErrorState() = runTest {
        whenever(fetchHeroes.execute(any()))
            .doAnswer { throw UnknownHostException() }

        whenever(getHeroes.execute(Unit))
            .thenReturn(flowOf(Result.success(listOf())))


        initViewModel()

        onEvents(
            viewModel
        ) { collectedStates ->
            assertEquals(
                listOf(
                    HeroesState.Loading,
                    defaultContent,
                    HeroesState.Error,
                ),
                collectedStates
            )

        }

    }

    private fun initViewModel() {
        viewModel = HeroesViewModel(
            getHeroes, getSquad, fetchHeroes
        )
    }


    companion object {
        val heroes = listOf(DummyEntities.hero)
        val squad = listOf(DummyEntities.hero)

        val defaultContent = HeroesState.Content(
            heroes = listOf(),
            squad = listOf()
        )
    }
}