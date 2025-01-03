package com.mustafacan.data.remote.repository

import com.mustafacan.data.local.datasource.roomdatabase.FavoriteAnimalsDao
import com.mustafacan.data.remote.datasource.DogsRemoteDataSource
import com.mustafacan.data.remote.extension.setFavoriteInfo
import com.mustafacan.data.remote.extension.setImages
import com.mustafacan.domain.model.dogs.Dog
import com.mustafacan.domain.model.response.ApiResponse
import com.mustafacan.domain.repository.api_repository.DogsRepository
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class DogsRepositoryImpl @Inject constructor(
    private val remoteDataSource: DogsRemoteDataSource,
    private val dao: FavoriteAnimalsDao
) : DogsRepository {

    override suspend fun getDogs(): ApiResponse<List<Dog>> {
        return coroutineScope {
            val dogListResponse = remoteDataSource.getDogs()
            dogListResponse.setImages()
            dogListResponse.setFavoriteInfo(dao)
            return@coroutineScope dogListResponse
        }
    }

    override suspend fun search(query: String): ApiResponse<List<Dog>> {
        return coroutineScope {
            val dogListResponse = remoteDataSource.search(query)
            dogListResponse.setImages()
            dogListResponse.setFavoriteInfo(dao)
            return@coroutineScope dogListResponse
        }

    }


}