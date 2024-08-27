package com.mustafacan.domain.usecase.birds

import com.mustafacan.domain.model.birds.Bird
import com.mustafacan.domain.model.response.ApiResponse
import com.mustafacan.domain.repository.BirdsRepository
import javax.inject.Inject

class GetBirdsUseCase @Inject constructor(private val repository: BirdsRepository) {
     suspend fun runUseCase(result: (ApiResponse<List<Bird>>) -> Unit) {
         result.invoke(repository.getBirds())
    }
}