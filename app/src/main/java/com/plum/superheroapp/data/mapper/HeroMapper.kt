package com.plum.superheroapp.data.mapper

import com.plum.superheroapp.data.entity.HeroDataEntity
import com.plum.superheroapp.data.network.entity.HeroNetworkEntity
import com.plum.superheroapp.domain.entity.HeroDomainEntity


fun HeroNetworkEntity.toData(): HeroDataEntity = HeroDataEntity(
    id = _id,
    allies = allies,
    enemies = enemies,
    films = films,
    imageUrl = imageUrl,
    name = name,
    parkAttractions = parkAttractions,
    shortFilms = shortFilms,
    tvShows = tvShows,
    url = url,
    videoGames = videoGames,
    isSquadMember = false
)


fun HeroDataEntity.toDomain(): HeroDomainEntity = HeroDomainEntity(
    id = id,
    allies = allies,
    enemies = enemies,
    films = films,
    imageUrl = imageUrl,
    name = name,
    parkAttractions = parkAttractions,
    shortFilms = shortFilms,
    tvShows = tvShows,
    url = url,
    videoGames = videoGames,
    isSquadMember = isSquadMember
)


fun HeroDomainEntity.toData(): HeroDataEntity = HeroDataEntity(
    id = id,
    allies = allies,
    enemies = enemies,
    films = films,
    imageUrl = imageUrl,
    name = name,
    parkAttractions = parkAttractions,
    shortFilms = shortFilms,
    tvShows = tvShows,
    url = url,
    videoGames = videoGames,
    isSquadMember = isSquadMember
)