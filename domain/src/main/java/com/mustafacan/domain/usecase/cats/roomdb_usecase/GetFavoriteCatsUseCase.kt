package com.mustafacan.domain.usecase.cats.roomdb_usecase

import com.mustafacan.domain.model.cats.Cat
import com.mustafacan.domain.repository.roomdb_repository.FavoriteAnimalsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteCatsUseCase @Inject constructor(private val repository: FavoriteAnimalsRepository) {
     suspend fun runUseCase(): Flow<List<Cat>> {
         return repository.getCatListFlow()
    }
}