package com.mustafacan.data.remote.api

import com.mustafacan.domain.model.birds.Bird
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BirdsServices {
    @GET("birds")
    suspend fun getBirds() : Response<List<Bird>>

    @GET("birds")
    suspend fun search(@Query("search") search: String) : Response<List<Bird>>
}