package com.mustafacan.data.remote.api

import com.mustafacan.domain.model.cats.Cat
import retrofit2.Response
import retrofit2.http.GET

interface CatsServices {
    @GET("cats")
    suspend fun getCats() : Response<List<Cat>>
}