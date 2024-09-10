package com.mustafacan.domain.usecase.dogs

import com.mustafacan.domain.model.dogs.Dog
import com.mustafacan.domain.repository.FavoriteAnimalsRepository
import javax.inject.Inject

class AddFavoriteDogToLocalDatabase @Inject constructor(private val repository: FavoriteAnimalsRepository) {
     suspend fun runUseCase(dog:Dog, success: () -> Unit) {
         try {
             repository.insertDog(dog)
             success()
         } catch (e: Exception) {}
    }
}