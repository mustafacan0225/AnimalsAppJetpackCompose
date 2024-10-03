package com.mustafacan.domain.usecase.cats.roomdb_usecase

import com.mustafacan.domain.model.cats.Cat
import com.mustafacan.domain.repository.roomdb_repository.FavoriteAnimalsRepository
import javax.inject.Inject

class DeleteFavoriteCatUseCase @Inject constructor(private val repository: FavoriteAnimalsRepository) {
     suspend fun runUseCase(cat: Cat): Boolean {
         try {
             repository.deleteCat(cat)
             return true
         } catch (e: Exception) {
             return false
         }
    }
}