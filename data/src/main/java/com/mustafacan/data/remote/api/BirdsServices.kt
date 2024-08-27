package com.mustafacan.data.remote.api

import com.mustafacan.domain.model.birds.Bird
import retrofit2.Response
import retrofit2.http.GET

interface BirdsServices {
    @GET("birds")
    suspend fun getBirds() : Response<List<Bird>>
}