package com.mustafacan.data.local.datasource.roomdatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.mustafacan.domain.model.birds.Bird
import com.mustafacan.domain.model.cats.Cat
import com.mustafacan.domain.model.dogs.Dog
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteAnimalsDao {
    @Query("SELECT * FROM favorite_dogs")
    fun getDogsFlow(): Flow<List<Dog>>

    @Query("SELECT * FROM favorite_dogs")
    fun getDogs(): List<Dog>

    @Insert
    suspend fun insertDog(dog: Dog)

    @Delete
    suspend fun deleteAllDog(list: List<Dog>)

    @Delete
    suspend fun deleteDog(dog: Dog)

    @Query("SELECT * FROM favorite_birds")
    fun getBirdsFlow(): Flow<List<Bird>>

    @Query("SELECT * FROM favorite_birds")
    fun getBirds(): List<Bird>

    @Insert
    suspend fun insertBird(bird: Bird)

    @Delete
    suspend fun deleteAllBird(list: List<Bird>)

    @Delete
    suspend fun deleteBird(bird: Bird)

    @Query("SELECT * FROM favorite_cats")
    fun getCatsFlow(): Flow<List<Cat>>

    @Query("SELECT * FROM favorite_cats")
    fun getCats(): List<Cat>

    @Insert
    suspend fun insertCat(cat: Cat)

    @Delete
    suspend fun deleteAllCat(list: List<Cat>)

    @Delete
    suspend fun deleteCat(cat: Cat)
}