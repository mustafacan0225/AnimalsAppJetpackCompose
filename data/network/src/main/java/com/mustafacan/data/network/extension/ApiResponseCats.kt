package com.mustafacan.data.network.extension

import com.mustafacan.data.network.datasource.ImagesOfAnimals
//import com.mustafacan.data.roomdb.FavoriteAnimalsDao
import com.mustafacan.domain.model.cats.Cat
import com.mustafacan.domain.model.response.ApiResponse
import com.mustafacan.domain.usecase.cats.roomdb_usecase.GetFavoriteCatsUseCase
import com.mustafacan.domain.usecase.cats.roomdb_usecase.GetFlowFavoriteCatsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.withContext

suspend fun ApiResponse<List<Cat>>.setImages() {
    when (this) {
        is ApiResponse.Success<List<Cat>> -> {
            this.data.forEach {
                it.image = ImagesOfAnimals.getImageUrlForCat(it.name?: "")
            }
        }

        is ApiResponse.Error -> {}
    }
}

suspend fun ApiResponse<List<Cat>>.setFavoriteInfo(getFavoriteCatsUseCase: GetFavoriteCatsUseCase) {
    try {
        when (this) {
            is ApiResponse.Success<List<Cat>> -> {
                withContext(Dispatchers.IO) {
                    val favoriteList =  getFavoriteCatsUseCase.runUseCase()
                    favoriteList.forEach { favoriteAnimal ->
                        this@setFavoriteInfo.data.find { it.id == favoriteAnimal.id }?.isFavorite = true
                    }
                }
            }

            is ApiResponse.Error -> {}
        }
    } catch (e: Exception) {}

}