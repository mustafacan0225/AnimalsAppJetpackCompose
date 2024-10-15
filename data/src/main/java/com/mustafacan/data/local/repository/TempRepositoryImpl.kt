package com.mustafacan.data.local.repository

import com.mustafacan.data.local.datasource.roomdatabase.FavoriteAnimalsDao
import com.mustafacan.data.remote.extension.setFavoriteInfo
import com.mustafacan.data.remote.extension.setImages
import com.mustafacan.data.util.TemporaryData
import com.mustafacan.domain.model.birds.Bird
import com.mustafacan.domain.model.cats.Cat
import com.mustafacan.domain.model.dogs.Dog
import com.mustafacan.domain.model.response.ApiResponse
import com.mustafacan.domain.repository.temp.TempRepository
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class TempRepositoryImpl@Inject constructor(
    private val dao: FavoriteAnimalsDao
): TempRepository {
    override suspend fun getTempDogs(): ApiResponse<List<Dog>> {
        return coroutineScope {
            val dogList = TemporaryData.getList<Dog>(TemporaryData.dogListJson)
            val dogListResponse = ApiResponse.Success(data = dogList)
            dogListResponse.setImages()
            dogListResponse.setFavoriteInfo(dao)
            return@coroutineScope dogListResponse
        }
    }

    override suspend fun getTempBird(): ApiResponse<List<Bird>> {
        return coroutineScope {
            val birdList = TemporaryData.getList<Bird>(TemporaryData.birdListJson)
            val birdListResponse = ApiResponse.Success(data = birdList)
            birdListResponse.setImages()
            birdListResponse.setFavoriteInfo(dao)
            return@coroutineScope birdListResponse
        }    }

    override suspend fun getTempCats(): ApiResponse<List<Cat>> {
        return coroutineScope {
            val catList = TemporaryData.getList<Cat>(TemporaryData.catListJson)
            val catListResponse = ApiResponse.Success(data = catList)
            catListResponse.setImages()
            catListResponse.setFavoriteInfo(dao)
            return@coroutineScope catListResponse
        }
    }
}