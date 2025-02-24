package com.mustafacan.domain.repository.api

import com.mustafacan.domain.model.cats.Cat
import com.mustafacan.domain.model.response.ApiResponse

//remote repository
interface CatsRepository {
    suspend fun getCats(): ApiResponse<List<Cat>>
    suspend fun search(query: String): ApiResponse<List<Cat>>
}