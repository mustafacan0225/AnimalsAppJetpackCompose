package com.mustafacan.domain.usecase.dogs.sharedpreference

import com.mustafacan.domain.repository.sharedpreference.DogsSettingsRepository
import javax.inject.Inject

class GetSearchTypeUseCase @Inject constructor(private val repository: DogsSettingsRepository) {
    suspend fun runUseCase(): String? {
        return repository.getSearchTypeForDogList()
    }
}