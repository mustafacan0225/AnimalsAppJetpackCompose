package com.mustafacan.domain.repository

import com.mustafacan.domain.model.birds.Bird
import com.mustafacan.domain.model.response.ApiResponse

//remote repository
interface BirdsRepository {
    suspend fun getBirds(): ApiResponse<List<Bird>>
}