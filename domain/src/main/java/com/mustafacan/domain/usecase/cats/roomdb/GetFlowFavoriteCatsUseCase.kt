package com.mustafacan.domain.usecase.cats.roomdb

import com.mustafacan.domain.model.cats.Cat
import com.mustafacan.domain.repository.roomdb.FavoriteAnimalsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFlowFavoriteCatsUseCase @Inject constructor(private val repository: FavoriteAnimalsRepository) {
     suspend fun runUseCase(): Flow<List<Cat>> {
         return repository.getCatListFlow()
    }
}