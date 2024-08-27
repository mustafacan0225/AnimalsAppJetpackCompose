package com.mustafacan.data.remote.datasource

import com.mustafacan.data.remote.api.DogsServices
import com.mustafacan.data.remote.api.ResponseWrapper
import javax.inject.Inject

class DogsRemoteDataSource @Inject constructor(private val api: DogsServices) :
    ResponseWrapper() {

    suspend fun getDogs() = getResult { api.getDogs() }
}