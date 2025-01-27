package com.mustafacan.domain.usecase.cats.roomdb_usecase

import com.mustafacan.domain.model.cats.Cat
import com.mustafacan.domain.repository.roomdb.FavoriteAnimalsRepository
import javax.inject.Inject

class AddFavoriteCatUseCase @Inject constructor(private val repository: FavoriteAnimalsRepository) {
    suspend fun runUseCase(cat: Cat): Boolean {
        try {
            repository.insertCat(cat)
            return true
        } catch (e: Exception) {
            return false
        }
    }
}