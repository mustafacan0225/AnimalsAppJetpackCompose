package com.mustafacan.data.network.repository

import com.mustafacan.data.network.datasource.DogsRemoteDataSource
import com.mustafacan.data.network.extension.setFavoriteInfo
import com.mustafacan.data.network.extension.setImages
//import com.mustafacan.data.roomdb.FavoriteAnimalsDao
import com.mustafacan.domain.model.dogs.Dog
import com.mustafacan.domain.model.response.ApiResponse
import com.mustafacan.domain.repository.api_repository.DogsRepository
import com.mustafacan.domain.usecase.dogs.roomdb_usecase.GetFavoriteDogsUseCase
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class DogsRepositoryImpl @Inject constructor(
    private val remoteDataSource: DogsRemoteDataSource,
    private val getFavoriteDogsUseCase: GetFavoriteDogsUseCase
) : DogsRepository {

    override suspend fun getDogs(): ApiResponse<List<Dog>> {
        return coroutineScope {
            val dogListResponse = remoteDataSource.getDogs()
            dogListResponse.setImages()
            dogListResponse.setFavoriteInfo(getFavoriteDogsUseCase)
            return@coroutineScope dogListResponse
        }
    }

    override suspend fun search(query: String): ApiResponse<List<Dog>> {
        return coroutineScope {
            val dogListResponse = remoteDataSource.search(query)
            dogListResponse.setImages()
            dogListResponse.setFavoriteInfo(getFavoriteDogsUseCase)
            return@coroutineScope dogListResponse
        }

    }


}