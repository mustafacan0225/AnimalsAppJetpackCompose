package com.mustafacan.data.remote.repository

import com.mustafacan.data.local.datasource.roomdatabase.FavoriteAnimalsDao
import com.mustafacan.data.remote.Images
import com.mustafacan.data.remote.datasource.DogsRemoteDataSource
import com.mustafacan.domain.model.dogs.Dog
import com.mustafacan.domain.model.response.ApiResponse
import com.mustafacan.domain.repository.api_repository.DogsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DogsRepositoryImpl @Inject constructor(
    private val remoteDataSource: DogsRemoteDataSource,
    private val dao: FavoriteAnimalsDao
) : DogsRepository {
    override suspend fun getDogs(): ApiResponse<List<Dog>> {
        return coroutineScope {
            val dogListDeferred = async { remoteDataSource.getDogs() }
            val dogListResponse = dogListDeferred.await()
            setImages(dogListResponse)
            setFavoriteInfo(dogListResponse)
            return@coroutineScope dogListResponse
        }
    }

    override suspend fun search(query: String): ApiResponse<List<Dog>> {
        return coroutineScope {
            val dogListDeferred = async { remoteDataSource.search(query) }
            val dogListResponse = dogListDeferred.await()
            setImages(dogListResponse)
            setFavoriteInfo(dogListResponse)
            return@coroutineScope dogListResponse
        }

    }

    fun setImages(response: ApiResponse<List<Dog>>) {
        when (response) {
            is ApiResponse.Success<List<Dog>> -> {
                response.data.forEach {
                    it.image = Images.getImageUrlForDog(it.name?: "")
                }
            }

            is ApiResponse.Error -> {}
        }
    }

    suspend fun setFavoriteInfo(response: ApiResponse<List<Dog>>) {
        when (response) {
            is ApiResponse.Success<List<Dog>> -> {
                val favoriteAnimals = withContext(Dispatchers.IO) {
                    dao.getDogs()
                }

                favoriteAnimals.forEach { favoriteAnimal ->
                    response.data.find { it.id == favoriteAnimal.id }?.isFavorite = true
                }
            }

            is ApiResponse.Error -> {}
        }
    }

}