package com.mustafacan.domain.usecase.dogs

import com.mustafacan.domain.model.dogs.Dog
import com.mustafacan.domain.repository.FavoriteAnimalsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteDogsFromLocalDatabase @Inject constructor(private val repository: FavoriteAnimalsRepository) {
     suspend fun runUseCase(result: (Flow<List<Dog>>) -> Unit) {
         result.invoke(repository.getDogList())
    }
}