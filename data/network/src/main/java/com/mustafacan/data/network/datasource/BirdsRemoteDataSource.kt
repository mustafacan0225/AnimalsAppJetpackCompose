package com.mustafacan.data.network.datasource

import com.mustafacan.data.network.api.BirdsServices
import javax.inject.Inject

class BirdsRemoteDataSource @Inject constructor(private val api: BirdsServices) :
    ResponseWrapper() {

    suspend fun getBirds() = getResult { api.getBirds() }
    suspend fun search(query: String) = getResult { api.search(query) }
}