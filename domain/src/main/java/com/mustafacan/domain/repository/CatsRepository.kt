package com.mustafacan.domain.repository

import com.mustafacan.domain.model.cats.Cat
import com.mustafacan.domain.model.response.ApiResponse

interface CatsRepository {
    suspend fun getCats(): ApiResponse<List<Cat>>
}