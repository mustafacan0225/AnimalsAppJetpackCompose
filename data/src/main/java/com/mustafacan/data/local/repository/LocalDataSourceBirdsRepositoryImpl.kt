package com.mustafacan.data.local.repository

import com.mustafacan.data.local.datasource.sharedpref.birds.LocalDataSourceBirds
import com.mustafacan.domain.repository.sharedpref_repository.LocalDataSourceBirdsRepository
import javax.inject.Inject

class LocalDataSourceBirdsRepositoryImpl@Inject constructor(private val localDataSource: LocalDataSourceBirds): LocalDataSourceBirdsRepository {
    override suspend fun saveListTypeForBirdList(type: String) {
        localDataSource.saveListTypeForBirdList(type)
    }

    override suspend fun getListTypeForBirdList(): String? {
        return localDataSource.getListTypeForBirdList()
    }

    override suspend fun saveSettingsTypeBirdList(type: String) {
        localDataSource.saveSettingsTypeForBirdList(type)
    }

    override suspend fun getSettingsTypeBirdList(): String? {
        return localDataSource.getSettingsTypeForBirdList()
    }

    override suspend fun saveSearchTypeBirdList(type: String) {
        localDataSource.saveSearchTypeForBirdList(type)
    }

    override suspend fun getSearchTypeBirdList(): String? {
        return localDataSource.getSearchTypeForBirdList()
    }

}