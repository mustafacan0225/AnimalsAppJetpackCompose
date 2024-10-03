package com.mustafacan.domain.usecase.cats.sharedpref_usecase

import com.mustafacan.domain.repository.sharedpref_repository.LocalDataSourceCatsRepository
import javax.inject.Inject

class GetSearchTypeUseCase @Inject constructor(private val repository: LocalDataSourceCatsRepository) {
    suspend fun runUseCase(): String? {
        return repository.getSearchTypeCatList()
    }
}