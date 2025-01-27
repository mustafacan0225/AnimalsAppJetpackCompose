package com.mustafacan.data.network.extension

import com.mustafacan.data.network.datasource.ImagesOfAnimals
//import com.mustafacan.data.roomdb.FavoriteAnimalsDao
import com.mustafacan.domain.model.dogs.Dog
import com.mustafacan.domain.model.response.ApiResponse
import com.mustafacan.domain.usecase.dogs.roomdb_usecase.GetFavoriteDogsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun ApiResponse<List<Dog>>.setImages() {
    when (this) {
        is ApiResponse.Success<List<Dog>> -> {
            this.data.forEach {
                it.image = ImagesOfAnimals.getImageUrlForDog(it.name?: "")
            }
        }

        is ApiResponse.Error -> {}
    }
}

suspend fun ApiResponse<List<Dog>>.setFavoriteInfo(getFavoriteDogsUseCase: GetFavoriteDogsUseCase) {
    try {
        when (this) {
            is ApiResponse.Success<List<Dog>> -> {
                withContext(Dispatchers.IO) {
                    val favoriteList = getFavoriteDogsUseCase.runUseCase()
                    favoriteList.forEach { favoriteAnimal ->
                        this@setFavoriteInfo.data.find { it.id == favoriteAnimal.id }?.isFavorite = true
                    }
                }
            }

            is ApiResponse.Error -> {}
        }
    } catch (e: Exception) { }

}