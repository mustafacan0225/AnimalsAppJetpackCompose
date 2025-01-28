package com.mustafacan.domain.usecase.dogs.sharedpreference

import com.mustafacan.domain.repository.sharedpreference.DogsSettingsRepository
import javax.inject.Inject

class GetListTypeUseCase @Inject constructor(private val repository: DogsSettingsRepository) {
    suspend fun runUseCase(): String? {
        return repository.getListTypeForDogList()
    }
}