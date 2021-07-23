package com.example.foodreciepeapp.api

import com.example.foodreciepeapp.model.response.FoodReciepesData
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface FoodReciepeApi {

    @Headers("Authorization: Token 9c8b06d329136da358c2d00e76946b0111ce2c48")
    @GET("search")
    suspend fun getAllRecipies(
        @Query ("page") page : Int,
        @Query("query") query: String
    ): FoodReciepesData

}