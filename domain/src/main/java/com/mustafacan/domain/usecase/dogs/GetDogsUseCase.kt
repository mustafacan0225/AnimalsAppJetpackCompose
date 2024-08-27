package com.mustafacan.domain.usecase.dogs

import com.mustafacan.domain.model.dogs.Dog
import com.mustafacan.domain.model.response.ApiResponse
import com.mustafacan.domain.repository.DogsRepository
import javax.inject.Inject

class GetDogsUseCase @Inject constructor(private val repository: DogsRepository) {
     suspend fun runUseCase(result: (ApiResponse<List<Dog>>) -> Unit) {
         result.invoke(repository.getDogs())
    }
}