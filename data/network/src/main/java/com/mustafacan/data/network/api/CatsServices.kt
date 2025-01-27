package com.mustafacan.data.network.api

import com.mustafacan.domain.model.cats.Cat
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CatsServices {
    @GET("cats")
    suspend fun getCats() : Response<List<Cat>>

    @GET("cats")
    suspend fun search(@Query("search") search: String) : Response<List<Cat>>
}