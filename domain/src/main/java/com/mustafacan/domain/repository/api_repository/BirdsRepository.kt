package com.mustafacan.domain.repository.api_repository

import com.mustafacan.domain.model.birds.Bird
import com.mustafacan.domain.model.response.ApiResponse

//remote repository
interface BirdsRepository {
    suspend fun getBirds(): ApiResponse<List<Bird>>
    suspend fun search(query: String): ApiResponse<List<Bird>>
}