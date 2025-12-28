package com.plum.superheroapp.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.plum.superheroapp.data.datasource.handleApiCall
import com.plum.superheroapp.data.network.HeroesApi
import com.plum.superheroapp.data.network.entity.HeroNetworkEntity

class HeroPagingSource(
    private val heroApi: HeroesApi,
)  : PagingSource<Int, HeroNetworkEntity>() {
    override fun getRefreshKey(state: PagingState<Int, HeroNetworkEntity>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, HeroNetworkEntity> {
        return try {
            val pageNumber = params.key ?: 1
            val pageSize = params.loadSize

            val heroes = handleApiCall {
                heroApi.getHeroes(page = pageNumber)
            }.data

            return LoadResult.Page(
                data = heroes,
                prevKey = if (pageNumber == 1) null else pageNumber.minus(1),
                nextKey = if (heroes.size < pageSize) null else pageNumber.plus(1)
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}