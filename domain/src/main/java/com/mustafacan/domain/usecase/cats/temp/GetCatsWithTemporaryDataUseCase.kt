package com.mustafacan.domain.usecase.cats.temp

import com.mustafacan.domain.model.cats.Cat
import com.mustafacan.domain.model.response.ApiResponse
import com.mustafacan.domain.repository.temp.TempRepository
import javax.inject.Inject

class GetCatsWithTemporaryDataUseCase @Inject constructor(private val repository: TempRepository) {
     suspend fun runUseCase() : ApiResponse<List<Cat>> {
         return repository.getTempCats()
    }
}