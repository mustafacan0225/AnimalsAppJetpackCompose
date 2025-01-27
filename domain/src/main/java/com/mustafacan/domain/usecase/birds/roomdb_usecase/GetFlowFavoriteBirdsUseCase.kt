package com.mustafacan.domain.usecase.birds.roomdb_usecase

import com.mustafacan.domain.model.birds.Bird
import com.mustafacan.domain.repository.roomdb.FavoriteAnimalsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFlowFavoriteBirdsUseCase @Inject constructor(private val repository: FavoriteAnimalsRepository) {
     suspend fun runUseCase(): Flow<List<Bird>> {
         return repository.getBirdListFlow()
    }
}