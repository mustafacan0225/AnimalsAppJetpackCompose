package com.mustafacan.data.network.datasource

import com.mustafacan.data.network.api.CatsServices
import javax.inject.Inject

class CatsRemoteDataSource @Inject constructor(private val api: CatsServices) :
    ResponseWrapper() {

    suspend fun getCats() = getResult { api.getCats() }
    suspend fun search(query: String) = getResult { api.search(query) }
}