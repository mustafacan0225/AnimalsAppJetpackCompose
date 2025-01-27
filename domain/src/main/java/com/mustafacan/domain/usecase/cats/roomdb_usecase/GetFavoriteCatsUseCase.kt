package com.mustafacan.domain.usecase.cats.roomdb_usecase

import com.mustafacan.domain.model.cats.Cat
import com.mustafacan.domain.repository.roomdb.FavoriteAnimalsRepository
import javax.inject.Inject

class GetFavoriteCatsUseCase @Inject constructor(private val repository: FavoriteAnimalsRepository) {
     suspend fun runUseCase(): List<Cat> {
         return repository.getCatList()
    }
}