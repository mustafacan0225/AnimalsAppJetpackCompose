package com.mustafacan.data.network.extension

import com.mustafacan.data.network.datasource.ImagesOfAnimals
//import com.mustafacan.data.roomdb.FavoriteAnimalsDao
import com.mustafacan.domain.model.birds.Bird
import com.mustafacan.domain.model.response.ApiResponse
import com.mustafacan.domain.usecase.birds.roomdb_usecase.GetFavoriteBirdsUseCase
import com.mustafacan.domain.usecase.birds.roomdb_usecase.GetFlowFavoriteBirdsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.withContext

suspend fun ApiResponse<List<Bird>>.setImages() {
    when (this) {
        is ApiResponse.Success<List<Bird>> -> {
            this.data.forEach {
                it.image = ImagesOfAnimals.getImageUrlForBird(it.name?: "")
            }
        }

        is ApiResponse.Error -> {}
    }
}

suspend fun ApiResponse<List<Bird>>.setFavoriteInfo(getFavoriteBirdsUseCase: GetFavoriteBirdsUseCase) {
    try {
        when (this) {
            is ApiResponse.Success<List<Bird>> -> {
                 withContext(Dispatchers.IO) {
                     val favoriteList = getFavoriteBirdsUseCase.runUseCase()
                     favoriteList.forEach { favoriteAnimal ->
                         this@setFavoriteInfo.data.find { it.id == favoriteAnimal.id }?.isFavorite = true
                     }
                }
            }

            is ApiResponse.Error -> {}
        }
    } catch (e: Exception) {}

}