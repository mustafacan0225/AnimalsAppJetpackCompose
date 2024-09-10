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

    override suspend fun getDogList(): Flow<List<Dog>> {
        return dao.getAll()
    }


}