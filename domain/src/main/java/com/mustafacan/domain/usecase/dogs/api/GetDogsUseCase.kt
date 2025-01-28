package com.mustafacan.domain.usecase.dogs.api

import com.mustafacan.domain.model.dogs.Dog
import com.mustafacan.domain.model.response.ApiResponse
import com.mustafacan.domain.repository.api.DogsRepository
import javax.inject.Inject

class GetDogsUseCase @Inject constructor(private val repository: DogsRepository) {
     suspend fun runUseCase() : ApiResponse<List<Dog>> {
         return repository.getDogs()
    }
}