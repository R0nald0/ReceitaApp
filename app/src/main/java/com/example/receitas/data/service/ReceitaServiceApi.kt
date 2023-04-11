package com.example.receitas.data.service

import com.example.receitas.shared.constant.Const

import com.example.receitas.data.remote.model.Dto.AreaDTO
import com.example.receitas.data.remote.model.Dto.MealItem

import com.example.receitas.data.remote.retrofiApi.TheMealApi
import com.example.receitas.data.service.interf.IServiceApi
import retrofit2.Response
import javax.inject.Inject

class ReceitaServiceApi @Inject constructor(
     private var retrofitApi: TheMealApi
) : IServiceApi {


    override suspend fun getMealsFromArea(areaName:String): List<MealItem> {
        val  response =checkreResponse(retrofitApi.getMealFromArea(areaName))
        if (response != null) {
            if (response.isSuccessful){
                val item = response.body()?.meals
                if (item != null) {
                    return  item
                }else{
                    Const.exibilog("item nullo")
                }
            }else{
                Const.exibilog("erro: ${response.code()}")
            }
        }else{
            Const.exibilog("erro: Resposta nula ")
        }
        return listOf()
    }

    override suspend fun getMealByName(mealName: String): MealItem? {
        val  response =checkreResponse(retrofitApi.getMealByName(mealName))
        if (response != null) {
            if (response.isSuccessful){
                val item = response.body()?.listMeal
                if (item != null) {
                    return  item.get(0)
                }else{
                    Const.exibilog("item nullo")
                }
            }else{
                Const.exibilog("erro: ${response.code()}")
            }
        }else{
            Const.exibilog("erro: Resposta nula ")
        }
        return null
    }

    override suspend fun getArealMeal():  List<AreaDTO> {

        val response =checkreResponse(retrofitApi.getAreasMeal())

        if (response != null) {
            if (response.isSuccessful){
                val item = response.body()?.meals
                if (item != null) {
                    return  item
                }else{
                    Const.exibilog("item nullo")
                }
            }else{
                Const.exibilog("erro: ${response.code()}")
            }
        }else{
            Const.exibilog("erro: Resposta nula ")
        }
      return listOf()
    }

    private fun <T> checkreResponse(response:Response<T>):Response<T>?{
        try {
            response
        }catch (ex:Exception){
            Const.exibilog("erro: ${ex.stackTrace}")
            throw Exception("erro ao buscar Areas : ${ex.message}")
        }
        return response
    }

}