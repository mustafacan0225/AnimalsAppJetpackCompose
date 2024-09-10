package com.mustafacan.domain.repository

import com.mustafacan.domain.model.dogs.Dog
import kotlinx.coroutines.flow.Flow

interface FavoriteAnimalsRepository {
    suspend fun insertDog(dog: Dog)
    suspend fun getDogList(): Flow<List<Dog>>
}