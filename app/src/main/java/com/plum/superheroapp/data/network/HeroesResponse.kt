package com.plum.superheroapp.data.network

import com.plum.superheroapp.data.network.entity.HeroNetworkEntity


data class HeroesResponse(
    val info: Info,
    val data: List<HeroNetworkEntity>
)

data class Info(
    val totalPages: Int,
    val count: Int,
    val previousPage: String,
    val nextPage: String
)


