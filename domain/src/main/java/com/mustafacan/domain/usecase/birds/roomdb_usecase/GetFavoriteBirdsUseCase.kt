package com.mustafacan.domain.usecase.birds.roomdb_usecase

import com.mustafacan.domain.model.birds.Bird
import com.mustafacan.domain.repository.roomdb.FavoriteAnimalsRepository
import javax.inject.Inject

class GetFavoriteBirdsUseCase @Inject constructor(private val repository: FavoriteAnimalsRepository) {
     suspend fun runUseCase(): List<Bird> {
         return repository.getBirdList()
    }
}