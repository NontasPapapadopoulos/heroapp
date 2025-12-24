package com.plum.superheroapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(
    tableName = "hero",
)
data class HeroDataEntity(
    @PrimaryKey
    val id: Int,
    val allies: List<String>,
    val enemies: List<String>,
    val films: List<String>,
    val imageUrl: String?,
    val name: String,
    val parkAttractions: List<String>,
    val shortFilms: List<String>,
    val tvShows: List<String>,
    val url: String,
    val videoGames: List<String>,
    val isSquadMember: Boolean
)