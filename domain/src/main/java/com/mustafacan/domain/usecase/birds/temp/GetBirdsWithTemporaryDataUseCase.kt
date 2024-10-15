package com.mustafacan.domain.usecase.birds.temp

import com.mustafacan.domain.model.birds.Bird
import com.mustafacan.domain.model.response.ApiResponse
import com.mustafacan.domain.repository.temp.TempRepository
import javax.inject.Inject

class GetBirdsWithTemporaryDataUseCase @Inject constructor(private val repository: TempRepository) {
     suspend fun runUseCase() : ApiResponse<List<Bird>> {
         return repository.getTempBird()
    }
}