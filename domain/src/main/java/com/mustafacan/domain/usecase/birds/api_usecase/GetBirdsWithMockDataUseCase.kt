package com.mustafacan.domain.usecase.birds.api_usecase

import com.mustafacan.domain.model.birds.Bird
import com.mustafacan.domain.model.response.ApiResponse
import com.mustafacan.domain.repository.api_repository.BirdsRepository
import javax.inject.Inject

class GetBirdsWithMockDataUseCase @Inject constructor(private val repository: BirdsRepository) {
     suspend fun runUseCase() : ApiResponse<List<Bird>> {
         return repository.getBirdsWithMockData()
    }
}