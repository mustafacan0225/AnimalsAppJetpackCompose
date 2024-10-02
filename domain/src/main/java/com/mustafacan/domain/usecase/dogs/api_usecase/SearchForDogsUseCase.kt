package com.mustafacan.domain.usecase.dogs.api_usecase

import com.mustafacan.domain.model.dogs.Dog
import com.mustafacan.domain.model.response.ApiResponse
import com.mustafacan.domain.repository.api_repository.DogsRepository
import javax.inject.Inject

class SearchForDogsUseCase @Inject constructor(private val repository: DogsRepository) {
    suspend fun runUseCase(query: String): ApiResponse<List<Dog>> {
        return repository.search(query)
    }
}