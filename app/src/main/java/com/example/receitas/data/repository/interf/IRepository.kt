package com.example.receitas.data.repository.interf

import com.example.receitas.domain.model.Area
import com.example.receitas.domain.model.Receita

interface IRepository {
    suspend  fun getUserListReceitasDb():List<Receita>
    suspend fun recuperarReceitasPorArea(areaName: String): List<Receita>
    suspend  fun recuperarListaArea():List<Area>
    suspend  fun saveListAreaApiToDb():Boolean

    suspend fun recuperaReceitaPorNome(nomeReceita:String):Receita?
    suspend fun recuperaReceitaId(receita: Receita):Receita?
    suspend fun criarReceita(receita: Receita):Boolean
    suspend fun atualizarReceita(receita :Receita):Boolean
    suspend fun deletarReceita(receita: Receita):Boolean
    suspend fun  perquisarReceita(pequisa:String):List<Receita>

     fun getListBanner() :List<Receita>
}