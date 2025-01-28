package com.mustafacan.domain.usecase.dogs.roomdb

import com.mustafacan.domain.model.dogs.Dog
import com.mustafacan.domain.repository.roomdb.FavoriteAnimalsRepository
import javax.inject.Inject

class DeleteFavoriteDogUseCase @Inject constructor(private val repository: FavoriteAnimalsRepository) {
     suspend fun runUseCase(dog:Dog): Boolean {
         try {
             repository.deleteDog(dog)
             return true
         } catch (e: Exception) {
             return false
         }
    }
}