package com.plum.superheroapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.plum.superheroapp.presentation.ui.screen.details.HeroDetailsScreen
import com.plum.superheroapp.presentation.ui.screen.heroes.HeroesScreen

@Composable
fun NavigationGraph(
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = HeroesScreen
    ) {

        composable<HeroesScreen> {
            HeroesScreen(
                navigateToHeroDetailsScreen = { heroId ->
                    navController.navigate(HeroDetailsScreen(heroId))
                }
            )
        }


        composable<HeroDetailsScreen> {
            HeroDetailsScreen(
                navigateBack = { navController.popBackStack() }
            )
        }
    }

}