package com.mustafacan.data.remote.extension

import com.mustafacan.data.local.datasource.roomdatabase.FavoriteAnimalsDao
import com.mustafacan.data.remote.Images
import com.mustafacan.domain.model.birds.Bird
import com.mustafacan.domain.model.response.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun ApiResponse<List<Bird>>.setImages() {
    when (this) {
        is ApiResponse.Success<List<Bird>> -> {
            this.data.forEach {
                it.image = Images.getImageUrlForBird(it.name?: "")
            }
        }

        is ApiResponse.Error -> {}
    }
}

suspend fun ApiResponse<List<Bird>>.setFavoriteInfo(dao: FavoriteAnimalsDao) {
    try {
        when (this) {
            is ApiResponse.Success<List<Bird>> -> {
                val favoriteAnimals = withContext(Dispatchers.IO) {
                    dao.getBirds()
                }

                favoriteAnimals.forEach { favoriteAnimal ->
                    this.data.find { it.id == favoriteAnimal.id }?.isFavorite = true
                }
            }

            is ApiResponse.Error -> {}
        }
    } catch (e: Exception) {}

}