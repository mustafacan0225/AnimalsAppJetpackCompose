package com.mustafacan.domain.usecase.cats.sharedpref_usecase

import com.mustafacan.domain.repository.sharedpreference.CatsSettingsRepository
import javax.inject.Inject

class GetListTypeUseCase @Inject constructor(private val repository: CatsSettingsRepository) {
    suspend fun runUseCase(): String? {
        return repository.getListTypeForCatList()
    }
}