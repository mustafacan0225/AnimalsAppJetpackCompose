package com.mustafacan.domain.usecase.birds.sharedpref_usecase

import com.mustafacan.domain.repository.sharedpref_repository.LocalDataSourceBirdsRepository
import javax.inject.Inject

class GetSettingsTypeUseCase @Inject constructor(private val repository: LocalDataSourceBirdsRepository) {
    suspend fun runUseCase(): String? {
        return repository.getSettingsTypeBirdList()
    }
}