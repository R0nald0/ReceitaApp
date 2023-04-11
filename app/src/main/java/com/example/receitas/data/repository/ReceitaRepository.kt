package com.example.receitas.data.repository

import com.example.receitas.data.local.database.AreaHelper
import com.example.receitas.data.local.model.toArea
import com.example.receitas.data.remote.model.Dto.toAreaDAO

import com.example.receitas.data.repository.interf.IRepository
import com.example.receitas.data.service.ReceitaBannerService
import com.example.receitas.data.service.interf.IServiceApi
import com.example.receitas.data.service.interf.IServiceReceitaDb
import com.example.receitas.domain.model.Area
import com.example.receitas.domain.model.Receita
import com.example.receitas.mapReceita.MapReceita
import com.example.receitas.shared.constant.Const
import javax.inject.Inject


class  ReceitaRepository @Inject constructor(
    private val service :IServiceReceitaDb,
    private val serviceApi :IServiceApi,
    private val receitaBannerService: ReceitaBannerService,
    private val areaService: AreaHelper
    ) :IRepository{

    override
    suspend fun perquisarReceita(pequisa: String): List<Receita> {
         val retorno = service.searchByName(pequisa)
        if (retorno.isNotEmpty()){
              val listReceita =  retorno.map {
                    MapReceita.receitaDaoToReceita(it)
              }
            return  listReceita
        }
        return listOf()
    }

    override
    suspend fun recuperarListaArea(): List<Area> {
          var listAreaDao = areaService.getAll()
          if (listAreaDao.isNotEmpty()){
              val listArea = listAreaDao.map{ areaDAO ->
                    areaDAO.toArea()
              }
              return listArea
          }else{
              val retorno  =  saveListAreaApiToDb()
              if (retorno){
                  listAreaDao = areaService.getAll()
                  return  listAreaDao.map{ areaDAO ->
                      areaDAO.toArea()
                  }
              }
              return listOf()
          }

    }
    override
    suspend fun saveListAreaApiToDb(): Boolean {
        val listAreaDto = serviceApi.getArealMeal()

        if (listAreaDto.isNotEmpty()){
            val listAreaDao = listAreaDto.map{areaDTO ->
                 areaDTO.toAreaDAO()
            }
            areaService.post(listAreaDao)
            return true
        }
        return false
    }
    override
    suspend fun getUserListReceitasDb(): List<Receita> {
          val listaReceitaApi = service.getAll()

        if (listaReceitaApi !=null){
             val listaReceitas =listaReceitaApi.map {
                  MapReceita.receitaDaoToReceita(it)
             }

            return  listaReceitas
        }
        return emptyList()
    }

    override
    suspend fun recuperarReceitasPorArea(areaName: String): List<Receita> {
          if (areaName.isNotEmpty()){
              val listaReceitaData = serviceApi.getMealsFromArea(areaName).map {
                  MapReceita.convertMealListItemToReceitaData(it)
              }
            val listaReceita = listaReceitaData.map {
                 MapReceita.receitaDaoToReceita(it)
              }

              return  listaReceita
          }else{
              return listOf()
          }
    }
    override
    suspend fun recuperaReceitaId(receita: Receita): Receita {
        val  recetabd=MapReceita.receitaToReceitaDAO(receita)
        return MapReceita.receitaDaoToReceita(recetabd)
    }
    override suspend fun recuperaReceitaPorNome(nomeReceita: String): Receita? {
           if (nomeReceita !=null){
                val meal = serviceApi.getMealByName(nomeReceita)
               if (meal != null) {
                return  MapReceita.receitaDaoToReceita(
                       MapReceita.convertMealListItemToReceitaData(meal)
                  )
               }
           }
        return null
    }

    override  suspend fun criarReceita(receita: Receita): Boolean {

         val receitaData = MapReceita.receitaToReceitaDAO(receita)
        return   service.post(receitaData)
    }

    override  suspend fun atualizarReceita( receita: Receita): Boolean {
          val receitaData = MapReceita.receitaToReceitaDAO(receita)
          val receitaAtualizado = service.putch( receitaData)
          return receitaAtualizado
     }

    override suspend fun deletarReceita(receita: Receita) :Boolean {
        val  receitaData = MapReceita.receitaToReceitaDAO(receita)
        return service.delete(receitaData._idRealme)
    }

    override fun getListBanner(): List<Receita> {
         val listaBannerReceita = receitaBannerService.getReceitasBannerList()
          if (listaBannerReceita.isNotEmpty()){
                   val receitasBanners =listaBannerReceita.map {receitaData->
                        MapReceita.receitaDaoToReceita(receitaData)
                   }
              return receitasBanners
          }else{
              return  listOf()
          }

    }

}