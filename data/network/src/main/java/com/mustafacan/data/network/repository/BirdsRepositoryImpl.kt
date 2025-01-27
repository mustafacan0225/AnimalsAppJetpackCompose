package com.mustafacan.data.network.repository

import com.mustafacan.data.network.datasource.BirdsRemoteDataSource
import com.mustafacan.data.network.extension.setFavoriteInfo
import com.mustafacan.data.network.extension.setImages
//import com.mustafacan.data.roomdb.FavoriteAnimalsDao
import com.mustafacan.domain.model.birds.Bird
import com.mustafacan.domain.model.response.ApiResponse
import com.mustafacan.domain.repository.api_repository.BirdsRepository
import com.mustafacan.domain.usecase.birds.roomdb_usecase.GetFavoriteBirdsUseCase
import com.mustafacan.domain.usecase.birds.roomdb_usecase.GetFlowFavoriteBirdsUseCase
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class BirdsRepositoryImpl @Inject constructor(private val remoteDataSource: BirdsRemoteDataSource, private val getFavoriteBirdsUseCase: GetFavoriteBirdsUseCase):
    BirdsRepository {
    override suspend fun getBirds(): ApiResponse<List<Bird>> {
        return coroutineScope {
            val birdListResponse = remoteDataSource.getBirds()
            birdListResponse.setImages()
            birdListResponse.setFavoriteInfo(getFavoriteBirdsUseCase)
            return@coroutineScope birdListResponse
        }
    }

    override suspend fun search(query: String): ApiResponse<List<Bird>> {
        return coroutineScope {
            val birdListResponse = remoteDataSource.search(query)
            birdListResponse.setImages()
            birdListResponse.setFavoriteInfo(getFavoriteBirdsUseCase)
            return@coroutineScope birdListResponse
        }
    }

}