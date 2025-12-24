package com.plum.superheroapp.data.datasource

import com.plum.superheroapp.data.network.HeroesApi
import com.plum.superheroapp.data.network.entity.HeroNetworkEntity
import javax.inject.Inject

interface HeroRemoteDataSource {
    suspend fun getHeroes(): List<HeroNetworkEntity>
}


class HeroRemoteDataSourceImpl @Inject constructor(
    private val heroesApi: HeroesApi
): HeroRemoteDataSource {

    override suspend fun getHeroes(): List<HeroNetworkEntity> {
//        val response = heroesApi.getHeroes()
//        if (response.isSuccessful && response.body() != null)
            return heroesApi.getHeroes().body()!!.data

    }


}


