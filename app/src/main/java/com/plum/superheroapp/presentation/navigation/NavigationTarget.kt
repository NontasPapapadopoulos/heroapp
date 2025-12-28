package com.plum.superheroapp.presentation.navigation

import kotlinx.serialization.Serializable


@Serializable
object HeroesScreen


@Serializable
data class HeroDetailsScreen(val heroId: Int)






enum class NavigationArgument(val param: String) {
    HeroId("heroId")
}