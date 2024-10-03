package com.mustafacan.data.local.repository

import com.mustafacan.data.local.datasource.sharedpref.cats.LocalDataSourceCats
import com.mustafacan.data.local.datasource.sharedpref.dogs.LocalDataSourceDogs
import com.mustafacan.domain.repository.sharedpref_repository.LocalDataSourceCatsRepository
import com.mustafacan.domain.repository.sharedpref_repository.LocalDataSourceDogsRepository
import javax.inject.Inject

class LocalDataSourceCatsRepositoryImpl@Inject constructor(private val localDataSource: LocalDataSourceCats):
    LocalDataSourceCatsRepository {
    override suspend fun saveListTypeForCatList(type: String) {
        localDataSource.saveListTypeForCatList(type)
    }

    override suspend fun getListTypeForCatList(): String? {
        return localDataSource.getListTypeForCatList()
    }

    override suspend fun saveSettingsTypeCatList(type: String) {
        localDataSource.saveSettingsTypeForCatList(type)
    }

    override suspend fun getSettingsTypeCatList(): String? {
        return localDataSource.getSettingsTypeForCatList()
    }

    override suspend fun saveSearchTypeCatList(type: String) {
        localDataSource.saveSearchTypeForCatList(type)
    }

    override suspend fun getSearchTypeCatList(): String? {
        return localDataSource.getSearchTypeForCatList()
    }

    override suspend fun saveTabTypeForCatDetail(type: String) {
        localDataSource.saveTabTypeForCatDetail(type)
    }

    override suspend fun getTabTypeForCatDetail(): String? {
        return localDataSource.getTabTypeForCatDetail()
    }

}