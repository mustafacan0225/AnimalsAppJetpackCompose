package com.mustafacan.data.di

import android.content.Context
import com.mustafacan.data.local.SharedPreferencesManager
import com.squareup.moshi.Moshi
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
    fun provideSharedPreferences(@ApplicationContext context: Context, moshi: Moshi): SharedPreferencesManager {
        return SharedPreferencesManager(context, moshi)
    }
}