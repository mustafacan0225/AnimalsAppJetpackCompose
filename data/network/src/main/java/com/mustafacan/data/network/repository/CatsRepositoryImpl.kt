package com.mustafacan.data.network.repository

import com.mustafacan.data.network.datasource.CatsRemoteDataSource
import com.mustafacan.data.network.extension.setFavoriteInfo
import com.mustafacan.data.network.extension.setImages
//import com.mustafacan.data.roomdb.FavoriteAnimalsDao
import com.mustafacan.domain.model.cats.Cat
import com.mustafacan.domain.model.response.ApiResponse
import com.mustafacan.domain.repository.api_repository.CatsRepository
import com.mustafacan.domain.usecase.cats.roomdb_usecase.GetFavoriteCatsUseCase
import com.mustafacan.domain.usecase.cats.roomdb_usecase.GetFlowFavoriteCatsUseCase
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class CatsRepositoryImpl @Inject constructor(private val remoteDataSource: CatsRemoteDataSource, private val getFavoriteCatsUseCase: GetFavoriteCatsUseCase
):
    CatsRepository {
    override suspend fun getCats(): ApiResponse<List<Cat>> {
        return coroutineScope {
            val catListResponse = remoteDataSource.getCats()
            catListResponse.setImages()
            catListResponse.setFavoriteInfo(getFavoriteCatsUseCase)
            return@coroutineScope catListResponse
        }
    }

    override suspend fun search(query: String): ApiResponse<List<Cat>> {
        return coroutineScope {
            val catListResponse = remoteDataSource.search(query)
            catListResponse.setImages()
            catListResponse.setFavoriteInfo(getFavoriteCatsUseCase)
            return@coroutineScope catListResponse
        }
    }

}