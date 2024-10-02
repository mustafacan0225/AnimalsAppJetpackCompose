package com.mustafacan.domain.usecase.dogs.roomdb_usecase

import com.mustafacan.domain.model.dogs.Dog
import com.mustafacan.domain.repository.roomdb_repository.FavoriteAnimalsRepository
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