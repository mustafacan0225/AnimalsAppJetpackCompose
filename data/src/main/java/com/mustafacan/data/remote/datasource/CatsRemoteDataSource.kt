package com.mustafacan.data.remote.datasource

import com.mustafacan.data.remote.api.CatsServices
import com.mustafacan.data.remote.api.ResponseWrapper
import javax.inject.Inject

class CatsRemoteDataSource @Inject constructor(private val api: CatsServices) :
    ResponseWrapper() {

    suspend fun getCats() = getResult { api.getCats() }
    suspend fun search(query: String) = getResult { api.search(query) }
}