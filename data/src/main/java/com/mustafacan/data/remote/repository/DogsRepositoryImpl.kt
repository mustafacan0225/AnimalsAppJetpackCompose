package com.mustafacan.data.remote.repository

import com.mustafacan.data.remote.datasource.DogsRemoteDataSource
import com.mustafacan.domain.model.dogs.Dog
import com.mustafacan.domain.model.response.ApiResponse
import com.mustafacan.domain.repository.DogsRepository
import javax.inject.Inject

class DogsRepositoryImpl @Inject constructor(private val remoteDataSource: DogsRemoteDataSource): DogsRepository {
    override suspend fun getDogs(): ApiResponse<List<Dog>> {
        return remoteDataSource.getDogs()
    }

    override suspend fun search(query: String): ApiResponse<List<Dog>> {
        return remoteDataSource.search(query)
    }

}