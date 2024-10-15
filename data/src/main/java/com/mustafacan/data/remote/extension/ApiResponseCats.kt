package com.mustafacan.data.remote.extension

import com.mustafacan.data.local.datasource.roomdatabase.FavoriteAnimalsDao
import com.mustafacan.data.remote.Images
import com.mustafacan.domain.model.cats.Cat
import com.mustafacan.domain.model.response.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun ApiResponse<List<Cat>>.setImages() {
    when (this) {
        is ApiResponse.Success<List<Cat>> -> {
            this.data.forEach {
                it.image = Images.getImageUrlForCat(it.name?: "")
            }
        }

        is ApiResponse.Error -> {}
    }
}

suspend fun ApiResponse<List<Cat>>.setFavoriteInfo(dao: FavoriteAnimalsDao) {
    when (this) {
        is ApiResponse.Success<List<Cat>> -> {
            val favoriteAnimals = withContext(Dispatchers.IO) {
                dao.getCats()
            }

            favoriteAnimals.forEach { favoriteAnimal ->
                this.data.find { it.id == favoriteAnimal.id }?.isFavorite = true
            }
        }

        is ApiResponse.Error -> {}
    }
}