package com.mustafacan.data.remote.repository

import com.mustafacan.data.local.datasource.roomdatabase.FavoriteAnimalsDao
import com.mustafacan.data.remote.datasource.CatsRemoteDataSource
import com.mustafacan.data.remote.extension.setFavoriteInfo
import com.mustafacan.data.remote.extension.setImages
import com.mustafacan.domain.model.cats.Cat
import com.mustafacan.domain.model.response.ApiResponse
import com.mustafacan.domain.repository.api_repository.CatsRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class CatsRepositoryImpl @Inject constructor(private val remoteDataSource: CatsRemoteDataSource, private val dao: FavoriteAnimalsDao):
    CatsRepository {
    override suspend fun getCats(): ApiResponse<List<Cat>> {
        return coroutineScope {
            val catListDeferred = async { remoteDataSource.getCats() }
            val catListResponse = catListDeferred.await()
            catListResponse.setImages()
            catListResponse.setFavoriteInfo(dao)
            return@coroutineScope catListResponse
        }
    }

    override suspend fun search(query: String): ApiResponse<List<Cat>> {
        return coroutineScope {
            val catListDeferred = async { remoteDataSource.search(query) }
            val catListResponse = catListDeferred.await()
            catListResponse.setImages()
            catListResponse.setFavoriteInfo(dao)
            return@coroutineScope catListResponse
        }
    }

}