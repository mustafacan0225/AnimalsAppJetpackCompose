package com.mustafacan.data.di

import com.mustafacan.data.local.datasource.sharedpref.birds.LocalDataSourceBirds
import com.mustafacan.data.local.datasource.sharedpref.dogs.LocalDataSourceDogs
import com.mustafacan.data.local.repository.LocalDataSourceBirdsRepositoryImpl
import com.mustafacan.data.local.repository.LocalDataSourceDogsRepositoryImpl
import com.mustafacan.data.remote.repository.BirdsRepositoryImpl
import com.mustafacan.data.remote.repository.CatsRepositoryImpl
import com.mustafacan.data.remote.repository.DogsRepositoryImpl
import com.mustafacan.domain.repository.api_repository.BirdsRepository
import com.mustafacan.domain.repository.api_repository.CatsRepository
import com.mustafacan.domain.repository.api_repository.DogsRepository
import com.mustafacan.domain.repository.sharedpref_repository.LocalDataSourceBirdsRepository
import com.mustafacan.domain.repository.sharedpref_repository.LocalDataSourceDogsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalRepositoryModule {

    @Singleton
    @Provides
    fun provideLocalDataSourceDogsRepository(localDataSourceDogs: LocalDataSourceDogs): LocalDataSourceDogsRepository {
        return LocalDataSourceDogsRepositoryImpl(localDataSourceDogs)
    }

    @Singleton
    @Provides
    fun provideLocalDataSourceBirdsRepository(localDataSourceBirds: LocalDataSourceBirds): LocalDataSourceBirdsRepository {
        return LocalDataSourceBirdsRepositoryImpl(localDataSourceBirds)
    }

}