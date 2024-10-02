package com.mustafacan.data.local.repository

import com.mustafacan.data.local.datasource.sharedpref.dogs.LocalDataSourceDogs
import com.mustafacan.domain.repository.sharedpref_repository.LocalDataSourceDogsRepository
import javax.inject.Inject

class LocalDataSourceDogsRepositoryImpl@Inject constructor(private val localDataSource: LocalDataSourceDogs): LocalDataSourceDogsRepository {
    override suspend fun saveListTypeForDogList(type: String) {
        localDataSource.saveListTypeForDogList(type)
    }

    override suspend fun getListTypeForDogList(): String? {
        return localDataSource.getListTypeForDogList()
    }

    override suspend fun saveSettingsTypeForDogList(type: String) {
        localDataSource.saveSettingsTypeForDogList(type)
    }

    override suspend fun getSettingsTypeForDogList(): String? {
        return localDataSource.getSettingsTypeForDogList()
    }

    override suspend fun saveSearchTypeForDogList(type: String) {
        localDataSource.saveSearchTypeForDogList(type)
    }

    override suspend fun getSearchTypeForDogList(): String? {
        return localDataSource.getSearchTypeForDogList()
    }

    override suspend fun saveTabTypeForDogDetail(type: String) {
        localDataSource.saveTabTypeForDogDetail(type)
    }

    override suspend fun getTabTypeForDogDetail(): String? {
        return localDataSource.getTabTypeForDogDetail()
    }
}