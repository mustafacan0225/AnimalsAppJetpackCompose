package com.mustafacan.data.roomdb

import com.mustafacan.domain.model.AllFavoriteAnimals
import com.mustafacan.domain.model.birds.Bird
import com.mustafacan.domain.model.cats.Cat
import com.mustafacan.domain.model.dogs.Dog
import com.mustafacan.domain.repository.roomdb.FavoriteAnimalsRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteAnimalsRepositoryImpl @Inject constructor(private val dao: FavoriteAnimalsDao) :
    FavoriteAnimalsRepository {

    private val allFavoriteAnimals = AllFavoriteAnimals()
    private val _allFavoriteAnimalsFlow = MutableStateFlow(AllFavoriteAnimals())

    init {
        listenFavoriteAnimals()
    }

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

    override suspend fun insertCat(cat: Cat) {
        dao.insertCat(cat)
    }

    override suspend fun deleteCat(cat: Cat) {
        dao.deleteCat(cat)
    }

    override suspend fun getCatListFlow(): Flow<List<Cat>> {
        return dao.getCatsFlow()
    }

    override suspend fun getCatList(): List<Cat> {
        return dao.getCats()
    }

    override suspend fun getAllFavoriteAnimals(): Flow<AllFavoriteAnimals> {
        return _allFavoriteAnimalsFlow
    }

    fun listenFavoriteAnimals() {
        val exceptionHandler = CoroutineExceptionHandler { _, e ->  }
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            launch {
                dao.getDogsFlow().stateIn(this).collectLatest {
                    allFavoriteAnimals.dogs = it
                    _allFavoriteAnimalsFlow.value = allFavoriteAnimals
                }
            }

            launch {
                dao.getCatsFlow().stateIn(this).collectLatest {
                    allFavoriteAnimals.cats = it
                    _allFavoriteAnimalsFlow.value = allFavoriteAnimals
                }
            }

            launch {
                dao.getBirdsFlow().stateIn(this).collectLatest {
                    allFavoriteAnimals.birds = it
                    _allFavoriteAnimalsFlow.value = allFavoriteAnimals
                }
            }
        }
    }

}