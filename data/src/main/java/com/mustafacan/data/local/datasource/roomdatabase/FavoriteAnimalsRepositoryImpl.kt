package com.mustafacan.data.local.datasource.roomdatabase

import com.mustafacan.domain.model.dogs.Dog
import com.mustafacan.domain.repository.FavoriteAnimalsRepository
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
        return dao.getAll()
    }

    override suspend fun getDogList(): List<Dog> {
        return dao.getDogs()
    }


}