package com.example.receitas.data.service.interf

import com.example.receitas.data.model.Dto.*

interface IServiceApi {
   suspend fun getMealsFromArea(areaName:String):List<MealItem>
   suspend fun getMealByName(mealName:String) : MealItem?
   suspend fun getArealMeal(): List<Area>
}