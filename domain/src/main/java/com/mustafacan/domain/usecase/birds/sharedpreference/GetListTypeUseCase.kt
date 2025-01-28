package com.mustafacan.domain.usecase.birds.sharedpreference

import com.mustafacan.domain.repository.sharedpreference.BirdsSettingsRepository
import javax.inject.Inject

class GetListTypeUseCase @Inject constructor(private val repository: BirdsSettingsRepository) {
    suspend fun runUseCase(): String? {
        return repository.getListTypeForBirdList()
    }
}