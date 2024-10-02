package com.mustafacan.domain.usecase.birds.api_usecase

import com.mustafacan.domain.model.birds.Bird
import com.mustafacan.domain.model.response.ApiResponse
import com.mustafacan.domain.repository.api_repository.BirdsRepository
import javax.inject.Inject

class SearchForBirdsUseCase @Inject constructor(private val repository: BirdsRepository) {
    suspend fun runUseCase(query: String): ApiResponse<List<Bird>> {
        return repository.search(query)
    }
}