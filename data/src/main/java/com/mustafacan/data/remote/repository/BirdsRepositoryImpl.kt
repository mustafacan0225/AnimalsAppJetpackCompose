package com.mustafacan.data.remote.repository

import com.mustafacan.data.local.datasource.roomdatabase.FavoriteAnimalsDao
import com.mustafacan.data.remote.Images
import com.mustafacan.data.remote.datasource.BirdsRemoteDataSource
import com.mustafacan.domain.model.birds.Bird
import com.mustafacan.domain.model.response.ApiResponse
import com.mustafacan.domain.repository.api_repository.BirdsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BirdsRepositoryImpl @Inject constructor(private val remoteDataSource: BirdsRemoteDataSource, private val dao: FavoriteAnimalsDao):
    BirdsRepository {
    override suspend fun getBirds(): ApiResponse<List<Bird>> {
        return coroutineScope {
            val birdListDeferred = async { remoteDataSource.getBirds() }
            val birdListResponse = birdListDeferred.await()
            setImages(birdListResponse)
            setFavoriteInfo(birdListResponse)
            return@coroutineScope birdListResponse
        }
    }

    override suspend fun search(query: String): ApiResponse<List<Bird>> {
        return coroutineScope {
            val birdListDeferred = async { remoteDataSource.search(query) }
            val birdListResponse = birdListDeferred.await()
            setImages(birdListResponse)
            setFavoriteInfo(birdListResponse)
            return@coroutineScope birdListResponse
        }
    }

    fun setImages(response: ApiResponse<List<Bird>>) {
        when (response) {
            is ApiResponse.Success<List<Bird>> -> {
                response.data.forEach {
                    it.image = Images.getImageUrlForBird(it.name?: "")
                }
            }

            is ApiResponse.Error -> {}
        }
    }

    suspend fun setFavoriteInfo(response: ApiResponse<List<Bird>>) {
        when (response) {
            is ApiResponse.Success<List<Bird>> -> {
                val favoriteAnimals = withContext(Dispatchers.IO) {
                    dao.getBirds()
                }

                favoriteAnimals.forEach { favoriteAnimal ->
                    response.data.find { it.id == favoriteAnimal.id }?.isFavorite = true
                }
            }

            is ApiResponse.Error -> {}
        }
    }
}