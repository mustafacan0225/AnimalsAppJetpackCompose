package com.mustafacan.data.remote.repository

import com.mustafacan.data.local.datasource.roomdatabase.FavoriteAnimalsDao
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

            return@coroutineScope getDataWithFavoriteInfo(birdListResponse)
        }
    }

    override suspend fun search(query: String): ApiResponse<List<Bird>> {

        return coroutineScope {
            val birdListDeferred = async { remoteDataSource.search(query) }
            val birdListResponse = birdListDeferred.await()

            return@coroutineScope getDataWithFavoriteInfo(birdListResponse)
        }
    }

    suspend fun getDataWithFavoriteInfo(response: ApiResponse<List<Bird>>): ApiResponse<List<Bird>> {
        when (response) {
            is ApiResponse.Success<List<Bird>> -> {

                //from room database
                val favoriteAnimals = withContext(Dispatchers.IO) {
                    dao.getBirds()
                }

                favoriteAnimals.forEach { favoriteAnimal ->
                    response.data.forEach {
                        if (it.id == favoriteAnimal.id) {
                            it.isFavorite = favoriteAnimal.isFavorite
                        }
                    }
                }

                return response
            }

            is ApiResponse.Error -> {
                return response
            }
        }
    }
}