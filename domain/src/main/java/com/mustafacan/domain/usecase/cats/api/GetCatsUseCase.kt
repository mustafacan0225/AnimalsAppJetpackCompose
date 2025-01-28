package com.mustafacan.domain.usecase.cats.api

import com.mustafacan.domain.model.cats.Cat
import com.mustafacan.domain.model.response.ApiResponse
import com.mustafacan.domain.repository.api.CatsRepository
import javax.inject.Inject

class GetCatsUseCase @Inject constructor(private val repository: CatsRepository) {
     suspend fun runUseCase(): ApiResponse<List<Cat>> {
         return repository.getCats()
    }
}