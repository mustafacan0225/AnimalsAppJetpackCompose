package com.mustafacan.data.network.api

import com.mustafacan.domain.model.dogs.Dog
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DogsServices {
    @GET("dogs")
    suspend fun getDogs() : Response<List<Dog>>

    @GET("dogs")
    suspend fun search(@Query("search") search: String) : Response<List<Dog>>
}