package com.mustafacan.data.di

import com.mustafacan.data.remote.repository.BirdsRepositoryImpl
import com.mustafacan.data.remote.repository.CatsRepositoryImpl
import com.mustafacan.data.remote.repository.DogsRepositoryImpl
import com.mustafacan.domain.repository.BirdsRepository
import com.mustafacan.domain.repository.CatsRepository
import com.mustafacan.domain.repository.DogsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideCatsRepository(repositoryImp: CatsRepositoryImpl): CatsRepository {
        return repositoryImp
    }

    @Singleton
    @Provides
    fun provideDogsRepository(repositoryImp: DogsRepositoryImpl): DogsRepository {
        return repositoryImp
    }

    @Singleton
    @Provides
    fun provideBirdsRepository(repositoryImp: BirdsRepositoryImpl): BirdsRepository {
        return repositoryImp
    }



}