package com.plum.superheroapp.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable


@Serializable
object HeroesScreen: NavKey


@Serializable
data class HeroDetailsScreen(val heroId: Int): NavKey






enum class NavigationArgument(val param: String) {
    HeroId("heroId")
}