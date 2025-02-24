package com.mustafacan.data.roomdb.di

import android.content.Context
import androidx.room.Room
import com.mustafacan.data.roomdb.FavoriteAnimalsDao
import com.mustafacan.data.roomdb.FavoriteAnimalsDatabase
import com.mustafacan.data.roomdb.FavoriteAnimalsRepositoryImpl
import com.mustafacan.domain.repository.roomdb.FavoriteAnimalsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DBModule {

    private const val DATABASE_NAME="favorite_animal_db"

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): FavoriteAnimalsDatabase {
        return Room.databaseBuilder(
            appContext,
            FavoriteAnimalsDatabase::class.java,
            DATABASE_NAME
        ).build()
    }


    @Singleton
    @Provides
    fun provideFavoriteAnimalsDao(database: FavoriteAnimalsDatabase): FavoriteAnimalsDao {
        return database.dao()
    }

    @Singleton
    @Provides
    fun provideFavoriteAnimalsRepository(favoriteAnimalsRepositoryImpl: FavoriteAnimalsRepositoryImpl): FavoriteAnimalsRepository {
        return favoriteAnimalsRepositoryImpl
    }
}