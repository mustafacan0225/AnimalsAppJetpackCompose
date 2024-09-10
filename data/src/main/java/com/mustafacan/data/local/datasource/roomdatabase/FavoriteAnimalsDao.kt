package com.mustafacan.data.local.datasource.roomdatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.mustafacan.domain.model.dogs.Dog
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteAnimalsDao {
    @Query("SELECT * FROM favorite_dogs")
    fun getAll(): Flow<List<Dog>>

    @Insert
    suspend fun insertDog(dog: Dog)

    @Delete
    suspend fun deleteAllDog(list: List<Dog>)

    @Delete
    suspend fun deleteDog(dog: Dog)
}