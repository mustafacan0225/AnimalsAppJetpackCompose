package com.mustafacan.domain.usecase.cats.api_usecase

import com.mustafacan.domain.model.cats.Cat
import com.mustafacan.domain.model.response.ApiResponse
import com.mustafacan.domain.repository.api_repository.CatsRepository
import javax.inject.Inject

class SearchForCatsUseCase @Inject constructor(private val repository: CatsRepository) {
    suspend fun runUseCase(query: String): ApiResponse<List<Cat>> {
        return repository.search(query)
    }
}