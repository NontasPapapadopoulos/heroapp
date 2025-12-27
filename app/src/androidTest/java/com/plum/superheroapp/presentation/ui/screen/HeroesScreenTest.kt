package com.plum.superheroapp.presentation.ui.screen

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.plum.superheroapp.presentation.DummyEntities
import com.plum.superheroapp.presentation.hero
import com.plum.superheroapp.presentation.ui.screen.heroes.HeroesEvent
import com.plum.superheroapp.presentation.ui.screen.heroes.HeroesScreen
import com.plum.superheroapp.presentation.ui.screen.heroes.HeroesScreenConstants.Companion.HERO
import com.plum.superheroapp.presentation.ui.screen.heroes.HeroesScreenConstants.Companion.SQUAD
import com.plum.superheroapp.presentation.ui.screen.heroes.HeroesState
import com.plum.superheroapp.presentation.ui.screen.heroes.HeroesViewModel
import com.plum.superheroapp.presentation.ui.screen.heroes.toHeroItems
import com.plum.superheroapp.presentation.ui.theme.SuperheroappTheme
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever


@RunWith(AndroidJUnit4::class)
class HeroesScreenTest {


    @get:Rule
    val composeTestRule = createComposeRule()


    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()


    @Mock
    private lateinit var viewModel: HeroesViewModel

    @Before
    fun setUp() {
        whenever(viewModel.errorFlow).thenReturn(MutableSharedFlow())
        whenever(viewModel.navigationFlow).thenReturn(MutableSharedFlow())
    }


    @Test
    fun onContentState_whenHeroItemIsClicked_addsSelectHero() {
        whenever(viewModel.uiState)
            .thenReturn(MutableStateFlow(defaultContent))

        composeTestRule.setContent {
            SuperheroappTheme {
                HeroesScreen(
                    viewModel = viewModel,
                    navigateToHeroDetailsScreen = {}
                )
            }
        }

        val id = 0
        composeTestRule.onNodeWithTag(HERO+0.toString()).performClick()

        verify(viewModel).add(HeroesEvent.SelectHero(id))
    }

    @Test
    fun onContentState_whenSquadItemIsClicked_addsSelectHero() {
        whenever(viewModel.uiState)
            .thenReturn(MutableStateFlow(defaultContent))

        composeTestRule.setContent {
            SuperheroappTheme {
                HeroesScreen(
                    viewModel = viewModel,
                    navigateToHeroDetailsScreen = {}
                )
            }
        }

        val id = 1
        composeTestRule.onNodeWithTag(SQUAD+1).performClick()

        verify(viewModel).add(HeroesEvent.SelectHero(id))
    }



    companion object {
        val defaultContent = HeroesState.Content(
            heroes = listOf(DummyEntities.hero.copy(id = 0)).toHeroItems(), //(0..10).map { DummyEntities.hero.copy(id = it) }.toHeroItems(),
            squad = listOf(DummyEntities.hero.copy(id = 1)).toHeroItems() //(11..15).map { DummyEntities.hero.copy(id = it) }.toHeroItems()
        )
    }
}
