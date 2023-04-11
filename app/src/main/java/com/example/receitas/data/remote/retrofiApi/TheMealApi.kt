package com.example.receitas.data.remote.retrofiApi

import com.example.receitas.data.remote.model.Dto.AreaList
import com.example.receitas.data.remote.model.Dto.Meals
import com.example.receitas.data.remote.model.Dto.MealAreaList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMealApi {

    @GET("filter.php?")
    suspend fun getMealFromArea(@Query("a") areaName: String):Response<MealAreaList>

    @GET("search.php")
    suspend fun getMealByName(@Query("s") mealName:String):Response<Meals>

    @GET("list.php?a=list")
    suspend fun  getAreasMeal():Response<AreaList>
}