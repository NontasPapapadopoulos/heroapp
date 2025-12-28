package com.plum.superheroapp.data.network

import com.plum.superheroapp.data.network.entity.HeroNetworkEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HeroesApi {

    @GET("/character")
    suspend fun getHeroes(@Query(value = "page") page: Int): Response<HeroesResponse>


}