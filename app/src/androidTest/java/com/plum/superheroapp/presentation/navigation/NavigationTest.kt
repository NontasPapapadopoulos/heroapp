package com.plum.superheroapp.presentation.navigation

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.plum.superheroapp.presentation.HiltComponentActivity
import com.plum.superheroapp.presentation.SuperHeroAndroidTest
import com.plum.superheroapp.presentation.ui.screen.heroes.HeroesScreenConstants.Companion.HERO
import com.plum.superheroapp.presentation.ui.screen.heroes.NavigationTarget
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@HiltAndroidTest
class NavigationTest: SuperHeroAndroidTest() {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<HiltComponentActivity>()

    lateinit var navController: TestNavHostController


    @Before
    fun setUpNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)

            navController.navigatorProvider.addNavigator(
                ComposeNavigator()
            )

            NavigationGraph(navController = navController)
        }
    }

    @Test
    fun navigateToHeroDetailsScreenTest() {
        composeTestRule.onNodeWithTag(HERO+0 ).performClick()
        assertTrue(navController.currentBackStackEntry?.destination?.hasRoute<HeroDetailsScreen>() ?: false)

    }

}