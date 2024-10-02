package com.mustafacan.domain.usecase.dogs.roomdb_usecase

import com.mustafacan.domain.model.dogs.Dog
import com.mustafacan.domain.repository.roomdb_repository.FavoriteAnimalsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteDogsUseCase @Inject constructor(private val repository: FavoriteAnimalsRepository) {
     suspend fun runUseCase(): Flow<List<Dog>> {
         return repository.getDogListFlow()
    }
}