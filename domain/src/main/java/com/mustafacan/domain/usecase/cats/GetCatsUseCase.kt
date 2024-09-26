package com.mustafacan.domain.usecase.cats

import com.mustafacan.domain.model.cats.Cat
import com.mustafacan.domain.model.response.ApiResponse
import com.mustafacan.domain.repository.CatsRepository
import javax.inject.Inject

class GetCatsUseCase @Inject constructor(private val repository: CatsRepository) {
     suspend fun runUseCase(): ApiResponse<List<Cat>> {
         return repository.getCats()
    }
}