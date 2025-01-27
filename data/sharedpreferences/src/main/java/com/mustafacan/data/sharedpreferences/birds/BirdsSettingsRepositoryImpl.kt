package com.mustafacan.data.sharedpreferences.birds

import com.mustafacan.domain.repository.sharedpreference.BirdsSettingsRepository
import javax.inject.Inject

class BirdsSettingsRepositoryImpl@Inject constructor(private val birdsSettings: BirdsSettings): BirdsSettingsRepository {
    override suspend fun saveListTypeForBirdList(type: String) {
        birdsSettings.saveListTypeForBirdList(type)
    }

    override suspend fun getListTypeForBirdList(): String? {
        return birdsSettings.getListTypeForBirdList()
    }

    override suspend fun saveSettingsTypeBirdList(type: String) {
        birdsSettings.saveSettingsTypeForBirdList(type)
    }

    override suspend fun getSettingsTypeBirdList(): String? {
        return birdsSettings.getSettingsTypeForBirdList()
    }

    override suspend fun saveSearchTypeBirdList(type: String) {
        birdsSettings.saveSearchTypeForBirdList(type)
    }

    override suspend fun getSearchTypeBirdList(): String? {
        return birdsSettings.getSearchTypeForBirdList()
    }

}