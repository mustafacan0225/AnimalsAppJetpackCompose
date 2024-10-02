package com.mustafacan.domain.repository.roomdb_repository

import com.mustafacan.domain.model.birds.Bird
import com.mustafacan.domain.model.dogs.Dog
import kotlinx.coroutines.flow.Flow

interface FavoriteAnimalsRepository {
    suspend fun insertDog(dog: Dog)
    suspend fun deleteDog(dog: Dog)
    suspend fun getDogListFlow(): Flow<List<Dog>>
    suspend fun getDogList(): List<Dog>

    suspend fun insertBird(bird: Bird)
    suspend fun deleteBird(bird: Bird)
    suspend fun getBirdListFlow(): Flow<List<Bird>>
    suspend fun getBirdList(): List<Bird>
}