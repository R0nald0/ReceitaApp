package com.example.receitas.data.model.Dto

import com.google.gson.annotations.SerializedName

data class Meals(
    @SerializedName("meals")
    val listMeal: List<MealItem>
)