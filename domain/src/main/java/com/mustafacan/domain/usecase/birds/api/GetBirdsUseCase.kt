package com.mustafacan.domain.usecase.birds.api

import com.mustafacan.domain.model.birds.Bird
import com.mustafacan.domain.model.response.ApiResponse
import com.mustafacan.domain.repository.api.BirdsRepository
import javax.inject.Inject

class GetBirdsUseCase @Inject constructor(private val repository: BirdsRepository) {
     suspend fun runUseCase(): ApiResponse<List<Bird>> {
         return repository.getBirds()
    }
}