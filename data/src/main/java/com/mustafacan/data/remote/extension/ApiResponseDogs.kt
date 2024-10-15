package com.mustafacan.data.remote.extension

import com.mustafacan.data.local.datasource.roomdatabase.FavoriteAnimalsDao
import com.mustafacan.data.remote.Images
import com.mustafacan.domain.model.dogs.Dog
import com.mustafacan.domain.model.response.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun ApiResponse<List<Dog>>.setImages() {
    when (this) {
        is ApiResponse.Success<List<Dog>> -> {
            this.data.forEach {
                it.image = Images.getImageUrlForDog(it.name?: "")
            }
        }

        is ApiResponse.Error -> {}
    }
}

suspend fun ApiResponse<List<Dog>>.setFavoriteInfo(dao: FavoriteAnimalsDao) {
    when (this) {
        is ApiResponse.Success<List<Dog>> -> {
            val favoriteAnimals = withContext(Dispatchers.IO) {
                dao.getDogs()
            }

            favoriteAnimals.forEach { favoriteAnimal ->
                this.data.find { it.id == favoriteAnimal.id }?.isFavorite = true
            }
        }

        is ApiResponse.Error -> {}
    }
}