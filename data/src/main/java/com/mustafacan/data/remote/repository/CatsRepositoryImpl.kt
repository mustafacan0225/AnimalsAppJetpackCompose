package com.mustafacan.data.remote.repository

import com.mustafacan.data.local.datasource.roomdatabase.FavoriteAnimalsDao
import com.mustafacan.data.remote.datasource.CatsRemoteDataSource
import com.mustafacan.domain.model.cats.Cat
import com.mustafacan.domain.model.response.ApiResponse
import com.mustafacan.domain.repository.api_repository.CatsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CatsRepositoryImpl @Inject constructor(private val remoteDataSource: CatsRemoteDataSource, private val dao: FavoriteAnimalsDao):
    CatsRepository {
    override suspend fun getCats(): ApiResponse<List<Cat>> {
        return coroutineScope {
            val catListDeferred = async { remoteDataSource.getCats() }
            val catListResponse = catListDeferred.await()

            return@coroutineScope getDataWithFavoriteInfo(catListResponse)
        }
    }

    override suspend fun search(query: String): ApiResponse<List<Cat>> {

        return coroutineScope {
            val catListDeferred = async { remoteDataSource.search(query) }
            val catListResponse = catListDeferred.await()

            return@coroutineScope getDataWithFavoriteInfo(catListResponse)
        }
    }

    suspend fun getDataWithFavoriteInfo(response: ApiResponse<List<Cat>>): ApiResponse<List<Cat>> {
        when (response) {
            is ApiResponse.Success<List<Cat>> -> {

                //from room database
                val favoriteAnimals = withContext(Dispatchers.IO) {
                    dao.getCats()
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