package com.mustafacan.data.local.datasource.roomdatabase

import com.mustafacan.domain.model.dogs.Dog
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mustafacan.domain.model.birds.Bird
import com.mustafacan.domain.model.dogs.AnimalColorsConverters

@Database(entities = [Dog::class, Bird::class], version = 1)
@TypeConverters(AnimalColorsConverters::class)
abstract class FavoriteAnimalsDatabase: RoomDatabase() {
    abstract fun dao(): FavoriteAnimalsDao
}
