package com.mustafacan.domain.usecase.dogs.roomdb_usecase

import com.mustafacan.domain.model.dogs.Dog
import com.mustafacan.domain.repository.roomdb.FavoriteAnimalsRepository
import javax.inject.Inject

class AddFavoriteDogUseCase @Inject constructor(private val repository: FavoriteAnimalsRepository) {
     suspend fun runUseCase(dog:Dog): Boolean {
         try {
             repository.insertDog(dog)
             return true
         } catch (e: Exception) {
             return false
         }
    }
}