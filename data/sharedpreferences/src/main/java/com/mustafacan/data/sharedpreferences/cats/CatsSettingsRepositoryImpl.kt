package com.mustafacan.data.sharedpreferences.cats

import com.mustafacan.domain.repository.sharedpreference.CatsSettingsRepository
import javax.inject.Inject

class CatsSettingsRepositoryImpl@Inject constructor(private val catsSettings: CatsSettings):
    CatsSettingsRepository {
    override suspend fun saveListTypeForCatList(type: String) {
        catsSettings.saveListTypeForCatList(type)
    }

    override suspend fun getListTypeForCatList(): String? {
        return catsSettings.getListTypeForCatList()
    }

    override suspend fun saveSettingsTypeCatList(type: String) {
        catsSettings.saveSettingsTypeForCatList(type)
    }

    override suspend fun getSettingsTypeCatList(): String? {
        return catsSettings.getSettingsTypeForCatList()
    }

    override suspend fun saveSearchTypeCatList(type: String) {
        catsSettings.saveSearchTypeForCatList(type)
    }

    override suspend fun getSearchTypeCatList(): String? {
        return catsSettings.getSearchTypeForCatList()
    }

    override suspend fun saveTabTypeForCatDetail(type: String) {
        catsSettings.saveTabTypeForCatDetail(type)
    }

    override suspend fun getTabTypeForCatDetail(): String? {
        return catsSettings.getTabTypeForCatDetail()
    }

}