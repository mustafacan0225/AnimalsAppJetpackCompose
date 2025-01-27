package com.mustafacan.domain.repository.sharedpreference

interface BirdsSettingsRepository {
    suspend fun saveListTypeForBirdList(type: String)
    suspend fun getListTypeForBirdList(): String?
    suspend fun saveSettingsTypeBirdList(type: String)
    suspend fun getSettingsTypeBirdList(): String?
    suspend fun saveSearchTypeBirdList(type: String)
    suspend fun getSearchTypeBirdList(): String?
}