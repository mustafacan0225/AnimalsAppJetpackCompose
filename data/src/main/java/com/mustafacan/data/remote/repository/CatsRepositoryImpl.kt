package com.mustafacan.data.remote.repository

import com.mustafacan.data.local.datasource.roomdatabase.FavoriteAnimalsDao
import com.mustafacan.data.remote.Images
import com.mustafacan.data.remote.datasource.CatsRemoteDataSource
import com.mustafacan.data.util.TemporaryData
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
            setImages(catListResponse)
            setFavoriteInfo(catListResponse)
            return@coroutineScope catListResponse
        }
    }

    override suspend fun search(query: String): ApiResponse<List<Cat>> {
        return coroutineScope {
            val catListDeferred = async { remoteDataSource.search(query) }
            val catListResponse = catListDeferred.await()
            setImages(catListResponse)
            setFavoriteInfo(catListResponse)
            return@coroutineScope catListResponse
        }
    }

    override suspend fun getCatsWithMockData(): ApiResponse<List<Cat>> {
        return coroutineScope {
            val catList = TemporaryData.getList<Cat>(TemporaryData.catListJson)
            val catListResponse = ApiResponse.Success(data = catList)
            setImages(catListResponse)
            setFavoriteInfo(catListResponse)
            return@coroutineScope catListResponse
        }
    }

    fun setImages(response: ApiResponse<List<Cat>>) {
        when (response) {
            is ApiResponse.Success<List<Cat>> -> {
                response.data.forEach {
                    it.image = Images.getImageUrlForCat(it.name?: "")
                }
            }

            is ApiResponse.Error -> {}
        }
    }

    suspend fun setFavoriteInfo(response: ApiResponse<List<Cat>>) {
        when (response) {
            is ApiResponse.Success<List<Cat>> -> {
                val favoriteAnimals = withContext(Dispatchers.IO) {
                    dao.getCats()
                }

                favoriteAnimals.forEach { favoriteAnimal ->
                    response.data.find { it.id == favoriteAnimal.id }?.isFavorite = true
                }
            }

            is ApiResponse.Error -> {}
        }
    }
}