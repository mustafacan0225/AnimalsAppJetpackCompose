package com.mustafacan.data.remote.api

import com.mustafacan.domain.model.dogs.Dog
import retrofit2.Response
import retrofit2.http.GET

interface DogsServices {
    @GET("dogs")
    suspend fun getDogs() : Response<List<Dog>>
}