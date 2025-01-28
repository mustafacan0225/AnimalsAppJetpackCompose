package com.mustafacan.domain.usecase.cats.sharedpreference

import com.mustafacan.domain.repository.sharedpreference.CatsSettingsRepository
import javax.inject.Inject

class SaveListTypeUseCase @Inject constructor(private val repository: CatsSettingsRepository) {
    suspend fun runUseCase(type: String) {
        repository.saveListTypeForCatList(type)
    }
}