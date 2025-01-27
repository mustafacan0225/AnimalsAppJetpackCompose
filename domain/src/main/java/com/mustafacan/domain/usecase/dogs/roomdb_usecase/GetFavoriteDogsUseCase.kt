package com.mustafacan.domain.usecase.dogs.roomdb_usecase

import com.mustafacan.domain.model.dogs.Dog
import com.mustafacan.domain.repository.roomdb.FavoriteAnimalsRepository
import javax.inject.Inject

class GetFavoriteDogsUseCase @Inject constructor(private val repository: FavoriteAnimalsRepository) {
     suspend fun runUseCase(): List<Dog> {
         return repository.getDogList()
    }
}