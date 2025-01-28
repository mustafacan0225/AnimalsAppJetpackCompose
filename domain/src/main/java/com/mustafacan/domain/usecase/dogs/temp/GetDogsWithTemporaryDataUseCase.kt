package com.mustafacan.domain.usecase.dogs.temp

import com.mustafacan.domain.model.dogs.Dog
import com.mustafacan.domain.model.response.ApiResponse
import com.mustafacan.domain.repository.temp.TempRepository
import javax.inject.Inject

class GetDogsWithTemporaryDataUseCase @Inject constructor(private val repository: TempRepository) {
     suspend fun runUseCase() : ApiResponse<List<Dog>> {
         return repository.getTempDogs()
    }
}