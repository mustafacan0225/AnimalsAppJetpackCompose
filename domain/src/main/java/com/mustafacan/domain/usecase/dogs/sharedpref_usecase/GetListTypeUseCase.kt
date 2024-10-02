package com.mustafacan.domain.usecase.dogs.sharedpref_usecase

import com.mustafacan.domain.repository.sharedpref_repository.LocalDataSourceDogsRepository
import javax.inject.Inject

class GetListTypeUseCase @Inject constructor(private val repository: LocalDataSourceDogsRepository) {
    suspend fun runUseCase(): String? {
        return repository.getListTypeForDogList()
    }
}