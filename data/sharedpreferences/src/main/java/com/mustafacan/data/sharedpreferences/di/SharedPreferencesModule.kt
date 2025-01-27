package com.mustafacan.data.sharedpreferences.di

import android.content.Context
import com.mustafacan.data.sharedpreferences.SharedPreferencesManager
import com.mustafacan.data.sharedpreferences.birds.BirdsSettings
import com.mustafacan.data.sharedpreferences.birds.BirdsSettingsRepositoryImpl
import com.mustafacan.data.sharedpreferences.cats.CatsSettings
import com.mustafacan.data.sharedpreferences.cats.CatsSettingsRepositoryImpl
import com.mustafacan.data.sharedpreferences.dogs.DogsSettings
import com.mustafacan.data.sharedpreferences.dogs.DogsSettingsRepositoryImpl
import com.mustafacan.domain.repository.sharedpreference.BirdsSettingsRepository
import com.mustafacan.domain.repository.sharedpreference.CatsSettingsRepository
import com.mustafacan.domain.repository.sharedpreference.DogsSettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferencesModule {

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferencesManager {
        return SharedPreferencesManager(context)
    }

    @Singleton
    @Provides
    fun provideDogsSettings(dogsSettings: DogsSettings): DogsSettingsRepository {
        return DogsSettingsRepositoryImpl(dogsSettings)
    }

    @Singleton
    @Provides
    fun provideBirdsSettings(birdsSettings: BirdsSettings): BirdsSettingsRepository {
        return BirdsSettingsRepositoryImpl(birdsSettings)
    }

    @Singleton
    @Provides
    fun provideCatsSettings(catSettings: CatsSettings): CatsSettingsRepository {
        return CatsSettingsRepositoryImpl(catSettings)
    }

}