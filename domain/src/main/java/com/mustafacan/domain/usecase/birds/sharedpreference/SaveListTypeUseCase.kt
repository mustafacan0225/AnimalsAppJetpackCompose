package com.mustafacan.domain.usecase.birds.sharedpreference

import com.mustafacan.domain.repository.sharedpreference.BirdsSettingsRepository
import javax.inject.Inject

class SaveListTypeUseCase @Inject constructor(private val repository: BirdsSettingsRepository) {
    suspend fun runUseCase(type: String) {
        repository.saveListTypeForBirdList(type)
    }
}