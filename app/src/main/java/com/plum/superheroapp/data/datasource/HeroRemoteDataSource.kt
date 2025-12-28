package com.plum.superheroapp.data.datasource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.plum.superheroapp.data.network.HeroesApi
import com.plum.superheroapp.data.network.entity.HeroNetworkEntity
import com.plum.superheroapp.pagingSource.HeroPagingSource
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

interface HeroRemoteDataSource {
    suspend fun getHeroes(page: Int): List<HeroNetworkEntity>
}


class HeroRemoteDataSourceImpl @Inject constructor(
    private val heroesApi: HeroesApi
) : HeroRemoteDataSource {

        override suspend fun getHeroes(page: Int): List<HeroNetworkEntity> {
        return handleApiCall {
            heroesApi.getHeroes(page = page)
        }.data

    }

//    override suspend fun getHeroes(page: Int): Pager<Int, HeroNetworkEntity> {
//        return Pager(
//            config = PagingConfig(
//                pageSize = 50,
//                prefetchDistance = 1,
//                enablePlaceholders = false,
//            ),
//            pagingSourceFactory = {
//                HeroPagingSource(
//                    heroesApi
//                )
//            }
//        )
//    }

}


suspend inline fun <T> handleApiCall(
    apiCall: suspend () -> Response<T>
): T {
    try {
        val response = apiCall()

        if (!response.isSuccessful) {
            throw ApiErrorResponse(
                code = response.code(),
                message = response.errorBody()?.string()
            )
        }

        return response.body()
            ?: throw EmptyBodyException()

    } catch (e: HttpException) {
        throw ApiErrorResponse(
            code = e.code(),
            message = e.message()
        )
    }
}



class EmptyBodyException : Exception("Response body is empty")

class ApiErrorResponse(
    val code: Int,
    message: String?
) : Exception(message)





