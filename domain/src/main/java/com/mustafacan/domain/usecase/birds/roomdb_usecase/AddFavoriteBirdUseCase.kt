package com.mustafacan.domain.usecase.birds.roomdb_usecase

import com.mustafacan.domain.model.birds.Bird
import com.mustafacan.domain.repository.roomdb.FavoriteAnimalsRepository
import javax.inject.Inject

class AddFavoriteBirdUseCase @Inject constructor(private val repository: FavoriteAnimalsRepository) {
     suspend fun runUseCase(bird: Bird): Boolean {
         try {
             repository.insertBird(bird)
             return true
         } catch (e: Exception) {
             return false
         }
    }
}