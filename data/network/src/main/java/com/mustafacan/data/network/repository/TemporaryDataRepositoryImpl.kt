package com.mustafacan.data.network.repository

import com.mustafacan.data.network.datasource.TemporaryData
import com.mustafacan.data.network.extension.setFavoriteInfo
import com.mustafacan.data.network.extension.setImages
import com.mustafacan.domain.model.birds.Bird
import com.mustafacan.domain.model.cats.Cat
import com.mustafacan.domain.model.dogs.Dog
import com.mustafacan.domain.model.response.ApiResponse
import com.mustafacan.domain.repository.temp.TempRepository
import com.mustafacan.domain.usecase.birds.roomdb_usecase.GetFavoriteBirdsUseCase
import com.mustafacan.domain.usecase.birds.roomdb_usecase.GetFlowFavoriteBirdsUseCase
import com.mustafacan.domain.usecase.cats.roomdb_usecase.GetFavoriteCatsUseCase
import com.mustafacan.domain.usecase.cats.roomdb_usecase.GetFlowFavoriteCatsUseCase
import com.mustafacan.domain.usecase.dogs.roomdb_usecase.GetFavoriteDogsUseCase
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class TemporaryDataRepositoryImpl@Inject constructor(
    private val getFavoriteDogsUseCase: GetFavoriteDogsUseCase,
    private val getFavoriteCatsUseCase: GetFavoriteCatsUseCase,
    private val getFavoriteBirdsUseCase: GetFavoriteBirdsUseCase
): TempRepository {
    override suspend fun getTempDogs(): ApiResponse<List<Dog>> {
        return coroutineScope {
            val dogList = TemporaryData.getList<Dog>(TemporaryData.dogListJson)
            val dogListResponse = ApiResponse.Success(data = dogList)
            dogListResponse.setImages()
            dogListResponse.setFavoriteInfo(getFavoriteDogsUseCase)
            return@coroutineScope dogListResponse
        }
    }

    override suspend fun getTempBird(): ApiResponse<List<Bird>> {
        return coroutineScope {
            val birdList = TemporaryData.getList<Bird>(TemporaryData.birdListJson)
            val birdListResponse = ApiResponse.Success(data = birdList)
            birdListResponse.setImages()
            birdListResponse.setFavoriteInfo(getFavoriteBirdsUseCase)
            return@coroutineScope birdListResponse
        }
    }

    override suspend fun getTempCats(): ApiResponse<List<Cat>> {
        return coroutineScope {
            val catList = TemporaryData.getList<Cat>(TemporaryData.catListJson)
            val catListResponse = ApiResponse.Success(data = catList)
            catListResponse.setImages()
            catListResponse.setFavoriteInfo(getFavoriteCatsUseCase)
            return@coroutineScope catListResponse
        }
    }
}