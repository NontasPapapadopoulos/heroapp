package com.plum.superheroapp.domain.interactor

import com.plum.superheroapp.domain.entity.HeroDomainEntity


object DummyEntities

val DummyEntities.hero: HeroDomainEntity
    get() = HeroDomainEntity(
        id = 0,
        name = "",
        url = "",
        imageUrl = "",
        videoGames = listOf(),
        enemies = listOf(),
        allies = listOf(),
        tvShows = listOf(),
        parkAttractions = listOf(),
        shortFilms = listOf(),
        films = listOf(),
        isSquadMember = false,
    )

