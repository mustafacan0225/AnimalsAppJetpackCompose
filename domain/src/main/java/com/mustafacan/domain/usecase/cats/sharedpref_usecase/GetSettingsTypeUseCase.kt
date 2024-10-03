package com.mustafacan.domain.usecase.cats.sharedpref_usecase

import com.mustafacan.domain.repository.sharedpref_repository.LocalDataSourceCatsRepository
import com.mustafacan.domain.repository.sharedpref_repository.LocalDataSourceDogsRepository
import javax.inject.Inject

class GetSettingsTypeUseCase @Inject constructor(private val repository: LocalDataSourceCatsRepository) {
    suspend fun runUseCase(): String? {
        return repository.getSettingsTypeCatList()
    }
}