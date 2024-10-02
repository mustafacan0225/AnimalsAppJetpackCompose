package com.mustafacan.data.remote.repository

import com.mustafacan.data.remote.datasource.BirdsRemoteDataSource
import com.mustafacan.domain.model.birds.Bird
import com.mustafacan.domain.model.response.ApiResponse
import com.mustafacan.domain.repository.api_repository.BirdsRepository
import javax.inject.Inject

class BirdsRepositoryImpl @Inject constructor(private val remoteDataSource: BirdsRemoteDataSource):
    BirdsRepository {
    override suspend fun getBirds(): ApiResponse<List<Bird>> {
        return remoteDataSource.getBirds()
    }
}