package com.plum.superheroapp.presentation.ui.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.plum.superheroapp.presentation.DummyEntities
import com.plum.superheroapp.presentation.hero
import com.plum.superheroapp.presentation.ui.screen.details.HeroDetailsEvent
import com.plum.superheroapp.presentation.ui.screen.details.HeroDetailsScreen
import com.plum.superheroapp.presentation.ui.screen.details.HeroDetailsScreenConstants.Companion.FIRE_BUTTON
import com.plum.superheroapp.presentation.ui.screen.details.HeroDetailsScreenConstants.Companion.MESSAGE_BOTTOM_SHEET
import com.plum.superheroapp.presentation.ui.screen.details.HeroDetailsScreenConstants.Companion.UPDATE_SQUAD_MEMBER_BUTTON
import com.plum.superheroapp.presentation.ui.screen.details.HeroDetailsState
import com.plum.superheroapp.presentation.ui.screen.details.HeroDetailsViewModel
import com.plum.superheroapp.presentation.ui.screen.heroes.HeroesEvent
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
class HeroDetailsScreenTest {


    @get:Rule
    val composeTestRule = createComposeRule()


    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()


    @Mock
    private lateinit var viewModel: HeroDetailsViewModel

    @Before
    fun setUp() {
        whenever(viewModel.errorFlow).thenReturn(MutableSharedFlow())
    }


    @Test
    fun onContentState_whenUpdateSquadButtonIsClicked_addsUpdateSquadMember() {
        whenever(viewModel.uiState)
            .thenReturn(MutableStateFlow(defaultContent))

        composeTestRule.setContent {
            SuperheroappTheme {
                HeroDetailsScreen(
                    viewModel = viewModel,
                    navigateBack = {}
                )
            }
        }

        composeTestRule.onNodeWithTag(UPDATE_SQUAD_MEMBER_BUTTON)
            .performClick()

        verify(viewModel).add(HeroDetailsEvent.UpdateSquadMember(defaultContent.hero))
    }


    @Test
    fun onContentState_whenUpdateButtonIsClicked_showsWarningMessage() {
        whenever(viewModel.uiState)
            .thenReturn(MutableStateFlow(
                defaultContent.copy(hero.copy(isSquadMember = true)))
            )

        composeTestRule.setContent {
            SuperheroappTheme {
                HeroDetailsScreen(
                    viewModel = viewModel,
                    navigateBack = {}
                )
            }
        }

        composeTestRule.onNodeWithTag(UPDATE_SQUAD_MEMBER_BUTTON)
            .performClick()


        composeTestRule.onNodeWithTag(MESSAGE_BOTTOM_SHEET)
            .assertIsDisplayed()

    }


    @Test
    fun onContentState_whenFireButtonIsClicked_addsUpdateSquadMember() {
        val hero = defaultContent.hero.copy(isSquadMember = true)

        whenever(viewModel.uiState)
            .thenReturn(MutableStateFlow(
                defaultContent.copy(hero))
            )

        composeTestRule.setContent {
            SuperheroappTheme {
                HeroDetailsScreen(
                    viewModel = viewModel,
                    navigateBack = {}
                )
            }
        }

        composeTestRule.onNodeWithTag(UPDATE_SQUAD_MEMBER_BUTTON)
            .performClick()


        composeTestRule.onNodeWithTag(FIRE_BUTTON)
            .performClick()

        verify(viewModel).add(HeroDetailsEvent.UpdateSquadMember(hero))
    }


    companion object {
        val hero = DummyEntities.hero
        val defaultContent = HeroDetailsState.Content(
            hero = hero
        )
    }
}