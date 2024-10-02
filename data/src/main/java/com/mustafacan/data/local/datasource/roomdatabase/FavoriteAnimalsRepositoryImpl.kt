package com.mustafacan.data.local.datasource.roomdatabase

import com.mustafacan.domain.model.birds.Bird
import com.mustafacan.domain.model.dogs.Dog
import com.mustafacan.domain.repository.roomdb_repository.FavoriteAnimalsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteAnimalsRepositoryImpl @Inject constructor(private val dao: FavoriteAnimalsDao) :
    FavoriteAnimalsRepository {
    override suspend fun insertDog(dog: Dog) {
        dao.insertDog(dog)
    }

    override suspend fun deleteDog(dog: Dog) {
        dao.deleteDog(dog)
    }

    override suspend fun getDogListFlow(): Flow<List<Dog>> {
        return dao.getDogsFlow()
    }

    override suspend fun getDogList(): List<Dog> {
        return dao.getDogs()
    }

    override suspend fun insertBird(bird: Bird) {
        dao.insertBird(bird)
    }

    override suspend fun deleteBird(bird: Bird) {
        dao.deleteBird(bird)
    }

    override suspend fun getBirdListFlow(): Flow<List<Bird>> {
        return dao.getBirdsFlow()
    }

    override suspend fun getBirdList(): List<Bird> {
        return dao.getBirds()
    }


}