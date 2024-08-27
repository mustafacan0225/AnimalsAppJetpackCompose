package com.mustafacan.data.remote.repository

import com.mustafacan.data.remote.datasource.CatsRemoteDataSource
import com.mustafacan.domain.model.cats.Cat
import com.mustafacan.domain.model.response.ApiResponse
import com.mustafacan.domain.repository.CatsRepository
import javax.inject.Inject

class CatsRepositoryImpl @Inject constructor(private val remoteDataSource: CatsRemoteDataSource): CatsRepository {
    override suspend fun getCats(): ApiResponse<List<Cat>> {
        return remoteDataSource.getCats()
    }
}