package com.mustafacan.data.network.datasource

import com.mustafacan.data.network.api.DogsServices
import javax.inject.Inject

class DogsRemoteDataSource @Inject constructor(private val api: DogsServices) :
    ResponseWrapper() {

    suspend fun getDogs() = getResult { api.getDogs() }

    suspend fun search(query: String) = getResult { api.search(query) }
}