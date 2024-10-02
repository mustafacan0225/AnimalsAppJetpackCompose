package com.mustafacan.domain.repository.sharedpref_repository

interface LocalDataSourceDogsRepository {
    suspend fun saveListTypeForDogList(type: String)
    suspend fun getListTypeForDogList(): String?
    suspend fun saveSettingsTypeForDogList(type: String)
    suspend fun getSettingsTypeForDogList(): String?
    suspend fun saveSearchTypeForDogList(type: String)
    suspend fun getSearchTypeForDogList(): String?
    suspend fun saveTabTypeForDogDetail(type: String)
    suspend fun getTabTypeForDogDetail(): String?
}