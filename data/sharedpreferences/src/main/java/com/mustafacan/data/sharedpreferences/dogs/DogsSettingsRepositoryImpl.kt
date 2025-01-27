package com.mustafacan.data.sharedpreferences.dogs

import com.mustafacan.domain.repository.sharedpreference.DogsSettingsRepository
import javax.inject.Inject

class DogsSettingsRepositoryImpl@Inject constructor(private val dogsSettings: DogsSettings): DogsSettingsRepository {
    override suspend fun saveListTypeForDogList(type: String) {
        dogsSettings.saveListTypeForDogList(type)
    }

    override suspend fun getListTypeForDogList(): String? {
        return dogsSettings.getListTypeForDogList()
    }

    override suspend fun saveSettingsTypeForDogList(type: String) {
        dogsSettings.saveSettingsTypeForDogList(type)
    }

    override suspend fun getSettingsTypeForDogList(): String? {
        return dogsSettings.getSettingsTypeForDogList()
    }

    override suspend fun saveSearchTypeForDogList(type: String) {
        dogsSettings.saveSearchTypeForDogList(type)
    }

    override suspend fun getSearchTypeForDogList(): String? {
        return dogsSettings.getSearchTypeForDogList()
    }

    override suspend fun saveTabTypeForDogDetail(type: String) {
        dogsSettings.saveTabTypeForDogDetail(type)
    }

    override suspend fun getTabTypeForDogDetail(): String? {
        return dogsSettings.getTabTypeForDogDetail()
    }
}