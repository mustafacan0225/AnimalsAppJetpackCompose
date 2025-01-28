package com.mustafacan.domain.usecase.dogs.sharedpreference

import com.mustafacan.domain.repository.sharedpreference.DogsSettingsRepository
import javax.inject.Inject

class SaveSearchTypeUseCase @Inject constructor(private val repository: DogsSettingsRepository) {
    suspend fun runUseCase(type: String) {
        repository.saveSearchTypeForDogList(type)
    }
}