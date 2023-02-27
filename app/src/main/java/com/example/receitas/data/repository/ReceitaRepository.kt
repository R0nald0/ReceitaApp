package com.example.receitas.data.repository

import com.example.receitas.shared.constant.Const
import com.example.receitas.data.model.Dto.Area
import com.example.receitas.data.model.Dto.MealItem
import com.example.receitas.data.model.ReceitaData

import com.example.receitas.data.repository.interf.IRepository
import com.example.receitas.data.service.ReceitaServiceApi
import com.example.receitas.data.service.interf.IServiceReceita
import com.example.receitas.domain.model.Receita
import com.example.receitas.mapReceita.MapReceita
import javax.inject.Inject

class  ReceitaRepository @Inject constructor(
      private val service :IServiceReceita,
      private val serviceApi :ReceitaServiceApi
    ) :IRepository{

    override suspend fun recuperarListaArea(): List<Area> {
          val listArea = serviceApi.getArealMeal()
         if (listArea !=null){
              return listArea
         }
        return listOf()
    }
    override
    suspend fun recuperarListaReceitasApi(areaName:String): List<Receita> {
        val listApi   = serviceApi.getMealsFromArea(areaName)

        if (listApi !=null){
            var receitaData =ReceitaData()
            val listaReceit = listApi.map{
                receitaData = MapReceita.convertMealListItemToReceitaData(it)
                MapReceita.receitaDataToReceita(receitaData)
            }
            return  listaReceit
        }
        return emptyList()
    }
    override suspend fun recuperarListaReceitas(): List<Receita> {
          val listaReceitaApi = service.getAll()

        if (listaReceitaApi !=null){

             val listaReceitas =listaReceitaApi.map {
                  MapReceita.receitaDataToReceita(it)
             }
            listaReceitaApi.forEach {
                Const.exibilog("receita view : ${it._idRealme}")
            }

            return  listaReceitas
        }
        return emptyList()
    }

    override suspend fun recuperarReceitasPorArea(areaName: String): List<Receita> {
          if (areaName.isNotEmpty()){
              val listaReceitaData = serviceApi.getMealsFromArea(areaName).map {
                  MapReceita.convertMealListItemToReceitaData(it)
              }
            val listaReceita =listaReceitaData.map {
                 MapReceita.receitaDataToReceita(it)
              }

              return  listaReceita
          }else{
              return listOf()
          }
    }

    override suspend fun recuperaReceitaId(receita: Receita): Receita {
        val  recetabd=MapReceita.rceitaToReceitaData(receita)
        return MapReceita.receitaDataToReceita(recetabd)
    }
    override suspend fun recuperaReceitaPorNome(nomeReceita: String): Receita? {
           if (nomeReceita !=null){
                val meal = serviceApi.getMealByName(nomeReceita)
               if (meal != null) {
                return  MapReceita.receitaDataToReceita(
                       MapReceita.convertMealListItemToReceitaData(meal)
                  )
               }
           }
        return null
    }

    override  suspend fun criarReceita(receita: Receita): Boolean {
        Const.exibilog("id receita  ${receita.idRealm}")
         val receitaData = MapReceita.rceitaToReceitaData(receita)
        return   service.post(receitaData)
    }

    override  suspend fun atualizarReceita(id: Int, receita: Receita): Receita {
          val receitaAtualizado = service.putch(id, MapReceita.rceitaToReceitaData(receita))
           return MapReceita.receitaDataToReceita(receitaAtualizado)
     }

    override suspend fun deletarReceita(receita: Receita) :Boolean {
        val  receitaData = MapReceita.rceitaToReceitaData(receita)
          Const.exibilog("id receita del ${receitaData._idRealme}")
          return service.delete(receitaData._idRealme)
    }

}