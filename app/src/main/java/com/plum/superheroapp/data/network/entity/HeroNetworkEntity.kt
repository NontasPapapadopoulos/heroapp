package com.plum.superheroapp.data.network.entity

data class HeroNetworkEntity(
    val _id: Int,
    val allies: List<String>,
    val enemies: List<String>,
    val films: List<String>,
    val imageUrl: String,
    val name: String,
    val parkAttractions: List<String>,
    val shortFilms: List<String>,
    val tvShows: List<String>,
    val url: String,
    val videoGames: List<String>
)