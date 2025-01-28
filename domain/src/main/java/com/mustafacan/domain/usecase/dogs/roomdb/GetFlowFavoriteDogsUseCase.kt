package com.mustafacan.domain.usecase.dogs.roomdb

import com.mustafacan.domain.model.dogs.Dog
import com.mustafacan.domain.repository.roomdb.FavoriteAnimalsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFlowFavoriteDogsUseCase @Inject constructor(private val repository: FavoriteAnimalsRepository) {
     suspend fun runUseCase(): Flow<List<Dog>> {
         return repository.getDogListFlow()
    }
}