package com.mustafacan.domain.repository.api

import com.mustafacan.domain.model.dogs.Dog
import com.mustafacan.domain.model.response.ApiResponse

//remote repository
interface DogsRepository {
    suspend fun getDogs(): ApiResponse<List<Dog>>
    suspend fun search(query: String): ApiResponse<List<Dog>>
}