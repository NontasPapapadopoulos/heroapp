package com.plum.superheroapp.presentation.feature

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.plum.superheroapp.presentation.HiltComponentActivity
import com.plum.superheroapp.presentation.SuperHeroAndroidTest
import com.plum.superheroapp.presentation.navigation.NavigationGraph
import com.plum.superheroapp.presentation.ui.screen.details.HeroDetailsScreenConstants.Companion.FIRE_BUTTON
import com.plum.superheroapp.presentation.ui.screen.details.HeroDetailsScreenConstants.Companion.UPDATE_SQUAD_MEMBER_BUTTON
import com.plum.superheroapp.presentation.ui.screen.heroes.HeroesScreenConstants.Companion.HERO
import com.plum.superheroapp.presentation.ui.screen.heroes.HeroesScreenConstants.Companion.SQUAD
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@HiltAndroidTest
class FeatureTest: SuperHeroAndroidTest() {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<HiltComponentActivity>()

    lateinit var navController: TestNavHostController

    @Before
    fun setUpContent() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)


            navController.navigatorProvider.addNavigator(
                ComposeNavigator()
            )
            NavigationGraph(navController = navController)

        }
    }

    @Test
    fun hireHero() = runTest {
        // given
        val heroId = db.getHeroDao()
            .getHeroes()
            .first()
            .first { !it.isSquadMember }
            .id

        //when
        composeTestRule.onNodeWithTag(HERO+heroId)
            .performClick()

        composeTestRule.onNodeWithTag(UPDATE_SQUAD_MEMBER_BUTTON)
            .performClick()

        composeTestRule.waitForIdle()

        // then
        val updatedHero = db.getHeroDao()
            .getHero(heroId)


        assertTrue(updatedHero.first().isSquadMember)

    }

    @Test
    fun fireHero() = runTest {
        // given
        val heroId = db.getHeroDao()
            .getHeroes()
            .first()
            .first { it.isSquadMember }
            .id

        // when
        composeTestRule.onNodeWithTag(SQUAD+heroId)
            .performClick()

        composeTestRule.onNodeWithTag(UPDATE_SQUAD_MEMBER_BUTTON)
            .performClick()

        composeTestRule.onNodeWithTag(FIRE_BUTTON)
            .performClick()


        composeTestRule.waitForIdle()


        // then
        val updatedHero = db.getHeroDao()
            .getHero(heroId)


        assertTrue(!updatedHero.first().isSquadMember)
    }
}