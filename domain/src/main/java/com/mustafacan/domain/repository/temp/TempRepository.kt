package com.mustafacan.domain.repository.temp

import com.mustafacan.domain.model.birds.Bird
import com.mustafacan.domain.model.cats.Cat
import com.mustafacan.domain.model.dogs.Dog
import com.mustafacan.domain.model.response.ApiResponse

interface TempRepository {
    suspend fun getTempDogs(): ApiResponse<List<Dog>>
    suspend fun getTempCats(): ApiResponse<List<Cat>>
    suspend fun getTempBird(): ApiResponse<List<Bird>>
}