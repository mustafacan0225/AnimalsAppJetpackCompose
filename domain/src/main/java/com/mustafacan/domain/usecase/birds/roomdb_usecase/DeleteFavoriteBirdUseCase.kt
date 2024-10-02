package com.mustafacan.domain.usecase.birds.roomdb_usecase

import com.mustafacan.domain.model.birds.Bird
import com.mustafacan.domain.repository.roomdb_repository.FavoriteAnimalsRepository
import javax.inject.Inject

class DeleteFavoriteBirdUseCase @Inject constructor(private val repository: FavoriteAnimalsRepository) {
     suspend fun runUseCase(bird: Bird): Boolean {
         try {
             repository.deleteBird(bird)
             return true
         } catch (e: Exception) {
             return false
         }
    }
}