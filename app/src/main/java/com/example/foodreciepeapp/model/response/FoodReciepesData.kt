package com.example.foodreciepeapp.model.response

data class FoodReciepesData(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Result>
)