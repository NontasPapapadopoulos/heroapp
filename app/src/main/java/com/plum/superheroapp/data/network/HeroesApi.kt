package com.plum.superheroapp.data.network

import com.plum.superheroapp.data.network.entity.HeroNetworkEntity
import retrofit2.Response
import retrofit2.http.GET

interface HeroesApi {

    @GET("/character")
    suspend fun getHeroes(): Response<HeroesResponse>


}