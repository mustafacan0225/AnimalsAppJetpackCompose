package com.mustafacan.data.network.di

import com.mustafacan.data.network.repository.BirdsRepositoryImpl
import com.mustafacan.data.network.repository.CatsRepositoryImpl
import com.mustafacan.data.network.repository.DogsRepositoryImpl
import com.mustafacan.data.network.repository.TemporaryDataRepositoryImpl
import com.mustafacan.domain.repository.api_repository.BirdsRepository
import com.mustafacan.domain.repository.api_repository.CatsRepository
import com.mustafacan.domain.repository.api_repository.DogsRepository
import com.mustafacan.domain.repository.temp.TempRepository
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
    fun provideCatsRepository(repositoryImpl: CatsRepositoryImpl): CatsRepository {
        return repositoryImpl
    }

    @Singleton
    @Provides
    fun provideDogsRepository(repositoryImpl: DogsRepositoryImpl): DogsRepository {
        return repositoryImpl
    }

    @Singleton
    @Provides
    fun provideBirdsRepository(repositoryImpl: BirdsRepositoryImpl): BirdsRepository {
        return repositoryImpl
    }

    @Singleton
    @Provides
    fun provideTemporaryRepository(repositoryImpl: TemporaryDataRepositoryImpl): TempRepository {
        return repositoryImpl
    }

}