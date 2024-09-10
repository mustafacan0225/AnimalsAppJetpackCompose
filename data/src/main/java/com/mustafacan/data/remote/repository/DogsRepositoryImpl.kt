package com.mustafacan.data.remote.repository

import com.mustafacan.data.remote.datasource.DogsRemoteDataSource
import com.mustafacan.domain.model.dogs.Dog
import com.mustafacan.domain.model.response.ApiResponse
import com.mustafacan.domain.repository.DogsRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class DogsRepositoryImpl @Inject constructor(private val remoteDataSource: DogsRemoteDataSource): DogsRepository {
    override suspend fun getDogs(): ApiResponse<List<Dog>> {
       return coroutineScope {
            val resultDeferred = async { remoteDataSource.getDogs() }
            val result = resultDeferred.await()
            return@coroutineScope result
        }
    }

    override suspend fun search(query: String): ApiResponse<List<Dog>> {
        return remoteDataSource.search(query)
    }

}