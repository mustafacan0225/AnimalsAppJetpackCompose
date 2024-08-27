package com.mustafacan.domain.repository

import com.mustafacan.domain.model.dogs.Dog
import com.mustafacan.domain.model.response.ApiResponse

interface DogsRepository {
    suspend fun getDogs(): ApiResponse<List<Dog>>
}