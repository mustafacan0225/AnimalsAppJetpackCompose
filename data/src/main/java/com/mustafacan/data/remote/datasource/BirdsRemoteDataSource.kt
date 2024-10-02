package com.mustafacan.data.remote.datasource

import com.mustafacan.data.remote.api.BirdsServices
import com.mustafacan.data.remote.api.ResponseWrapper
import javax.inject.Inject

class BirdsRemoteDataSource @Inject constructor(private val api: BirdsServices) :
    ResponseWrapper() {

    suspend fun getBirds() = getResult { api.getBirds() }
    suspend fun search(query: String) = getResult { api.search(query) }
}