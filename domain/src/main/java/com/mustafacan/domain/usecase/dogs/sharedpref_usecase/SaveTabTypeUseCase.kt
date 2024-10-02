package com.mustafacan.domain.usecase.dogs.sharedpref_usecase

import com.mustafacan.domain.repository.sharedpref_repository.LocalDataSourceDogsRepository
import javax.inject.Inject

class SaveTabTypeUseCase @Inject constructor(private val repository: LocalDataSourceDogsRepository) {
    suspend fun runUseCase(type: String) {
        return repository.saveTabTypeForDogDetail(type)
    }
}