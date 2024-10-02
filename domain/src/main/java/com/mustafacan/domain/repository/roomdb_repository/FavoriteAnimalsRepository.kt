package com.mustafacan.domain.repository.roomdb_repository

import com.mustafacan.domain.model.dogs.Dog
import kotlinx.coroutines.flow.Flow

interface FavoriteAnimalsRepository {
    suspend fun insertDog(dog: Dog)
    suspend fun deleteDog(dog: Dog)
    suspend fun getDogListFlow(): Flow<List<Dog>>
    suspend fun getDogList(): List<Dog>
}