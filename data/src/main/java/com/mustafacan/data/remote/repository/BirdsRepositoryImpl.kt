package com.mustafacan.data.remote.repository

import com.mustafacan.data.local.datasource.roomdatabase.FavoriteAnimalsDao
import com.mustafacan.data.remote.datasource.BirdsRemoteDataSource
import com.mustafacan.data.remote.extension.setFavoriteInfo
import com.mustafacan.data.remote.extension.setImages
import com.mustafacan.domain.model.birds.Bird
import com.mustafacan.domain.model.response.ApiResponse
import com.mustafacan.domain.repository.api_repository.BirdsRepository
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class BirdsRepositoryImpl @Inject constructor(private val remoteDataSource: BirdsRemoteDataSource, private val dao: FavoriteAnimalsDao):
    BirdsRepository {
    override suspend fun getBirds(): ApiResponse<List<Bird>> {
        return coroutineScope {
            val birdListResponse = remoteDataSource.getBirds()
            birdListResponse.setImages()
            birdListResponse.setFavoriteInfo(dao)
            return@coroutineScope birdListResponse
        }
    }

    override suspend fun search(query: String): ApiResponse<List<Bird>> {
        return coroutineScope {
            val birdListResponse = remoteDataSource.search(query)
            birdListResponse.setImages()
            birdListResponse.setFavoriteInfo(dao)
            return@coroutineScope birdListResponse
        }
    }

}