package com.plum.superheroapp.presentation.ui.screen.details

import androidx.lifecycle.SavedStateHandle
import com.plum.superheroapp.domain.entity.HeroDomainEntity
import com.plum.superheroapp.domain.interactor.DummyEntities
import com.plum.superheroapp.domain.interactor.GetHero
import com.plum.superheroapp.domain.interactor.UpdateHero
import com.plum.superheroapp.domain.interactor.hero
import com.plum.superheroapp.presentation.InlineClassesAnswer
import com.plum.superheroapp.presentation.MainDispatcherRule
import com.plum.superheroapp.presentation.navigation.NavigationArgument
import com.plum.superheroapp.presentation.onEvents
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class HeroDetailsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: HeroDetailsViewModel


    @Mock
    private lateinit var getHero: GetHero

    @Mock
    private lateinit var updateHero: UpdateHero

    private val savedStateHandle: SavedStateHandle =
        SavedStateHandle(mapOf(NavigationArgument.HeroId.param to 1))

    private val heroFlow: MutableStateFlow<Result<HeroDomainEntity>> =
        MutableStateFlow(Result.success(hero))


    @Before
    fun setUp() = runTest {
        whenever(getHero.execute(any()))
            .thenReturn(flowOf(Result.success(hero)))


        whenever(updateHero.execute(any())).thenAnswer(InlineClassesAnswer { invocation ->
            val params = invocation.getArgument<UpdateHero.Params>(0)
            val hero = heroFlow.value.getOrThrow()
            emitHero(
               hero = hero.copy(isSquadMember = !params.hero.isSquadMember)
            )

            Result.success(Unit)

        })
    }


    private fun emitHero(hero: HeroDomainEntity) = runBlocking {
        heroFlow.emit(Result.success(hero))
    }


    @Test
    fun onFlowStart_returnsHero() = runTest {
        initViewModel()
        onEvents(
            viewModel
        ) { collectedStates ->
            assertEquals(
                listOf(
                    HeroDetailsState.Loading,
                    defaultContent
                ),
                collectedStates
            )
        }
    }


//    @Test
//    fun onUpdateSquadMember_changesIsSquadMember() = runTest {
//        initViewModel()
//
//        onEvents(
//            viewModel,
//            HeroDetailsEvent.UpdateSquadMember(hero)
//        ) { collectedStates ->
//            assertEquals(
//                listOf(
//                    HeroDetailsState.Loading,
//                    defaultContent,
//                    defaultContent.copy(
//                        hero = hero.copy(isSquadMember = !hero.isSquadMember)
//                    )
//                ),
//                collectedStates
//            )
//        }
//    }



    private fun initViewModel() {
        viewModel = HeroDetailsViewModel(
            getHero, updateHero, savedStateHandle
        )
    }


    companion object {
        val hero = DummyEntities.hero
            .copy(
                isSquadMember = false
            )
        val defaultContent = HeroDetailsState.Content(hero)
    }

}