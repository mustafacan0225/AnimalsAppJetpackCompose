package com.mustafacan.domain.repository.sharedpref_repository

interface LocalDataSourceCatsRepository {
    suspend fun saveListTypeForCatList(type: String)
    suspend fun getListTypeForCatList(): String?
    suspend fun saveSettingsTypeCatList(type: String)
    suspend fun getSettingsTypeCatList(): String?
    suspend fun saveSearchTypeCatList(type: String)
    suspend fun getSearchTypeCatList(): String?
    suspend fun saveTabTypeForCatDetail(type: String)
    suspend fun getTabTypeForCatDetail(): String?
}