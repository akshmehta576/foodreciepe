package com.example.foodreciepeapp.repository

import android.util.Log
import com.example.foodreciepeapp.api.FoodReciepeApi
import com.example.foodreciepeapp.model.response.FoodReciepesData
import com.example.foodreciepeapp.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped

class FoodReciepeRepository @Inject constructor(
    private val foodReciepeApi: FoodReciepeApi
) {

     suspend fun getFoodReciepeList(pagenumber : Int, category : String): Resource<FoodReciepesData> {
        val response = try {
            foodReciepeApi.getAllRecipies(pagenumber,category)
        } catch (e: Exception) {

            return Resource.Error("An error occured")
        }
         return Resource.Success(response)
    }

}