package com.mustafacan.domain.usecase.allanimals

import com.mustafacan.domain.model.AllFavoriteAnimals
import com.mustafacan.domain.repository.roomdb_repository.FavoriteAnimalsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllFavoriteAnimalsUseCase @Inject constructor(private val repository: FavoriteAnimalsRepository) {
     suspend fun runUseCase(): Flow<AllFavoriteAnimals> {
         return repository.getAllFavoriteAnimals()
    }
}