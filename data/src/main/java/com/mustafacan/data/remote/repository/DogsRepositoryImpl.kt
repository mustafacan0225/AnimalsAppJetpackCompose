package com.mustafacan.data.remote.repository

import com.mustafacan.data.local.datasource.roomdatabase.FavoriteAnimalsDao
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

            when (dogListResponse) {
                is ApiResponse.Success<List<Dog>> -> {

                    //from room database
                    val favoriteAnimals = withContext(Dispatchers.IO) {
                        dao.getDogs()
                    }

                    favoriteAnimals.forEach { favoriteAnimal ->
                        dogListResponse.data.forEach {
                            if (it.id == favoriteAnimal.id) {
                                it.isFavorite = favoriteAnimal.isFavorite
                            }
                        }
                    }

                    return@coroutineScope dogListResponse
                }

                is ApiResponse.Error -> {
                    return@coroutineScope dogListResponse
                }
            }
        }
    }

    override suspend fun search(query: String): ApiResponse<List<Dog>> {

        return coroutineScope {
            val dogListDeferred = async { remoteDataSource.search(query) }
            val dogListResponse = dogListDeferred.await()

            when (dogListResponse) {
                is ApiResponse.Success<List<Dog>> -> {

                    //from room database
                    val favoriteAnimals = withContext(Dispatchers.IO) {
                        dao.getDogs()
                    }

                    favoriteAnimals.forEach { favoriteAnimal ->
                        dogListResponse.data.forEach {
                            if (it.id == favoriteAnimal.id) {
                                it.isFavorite = favoriteAnimal.isFavorite
                            }
                        }
                    }

                    return@coroutineScope dogListResponse
                }

                is ApiResponse.Error<List<Dog>> -> {
                    return@coroutineScope dogListResponse
                }
            }
        }

    }

}