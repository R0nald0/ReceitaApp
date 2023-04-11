package com.example.receitas.data.service.interf


import com.example.receitas.data.remote.model.Dto.AreaDTO
import com.example.receitas.data.remote.model.Dto.MealItem

interface IServiceApi {
   suspend fun getMealsFromArea(areaName:String):List<MealItem>
   suspend fun getMealByName(mealName:String) : MealItem?
   suspend fun getArealMeal(): List<AreaDTO>
}