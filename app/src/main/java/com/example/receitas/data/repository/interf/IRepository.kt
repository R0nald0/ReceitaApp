package com.example.receitas.data.repository.interf

import com.example.receitas.data.model.Dto.Area
import com.example.receitas.data.model.Dto.MealItem
import com.example.receitas.domain.model.Receita

interface IRepository {
    suspend  fun recuperarListaReceitas():List<Receita>
    suspend fun recuperarReceitasPorArea(areaName: String): List<Receita>
    suspend  fun recuperarListaArea():List<Area>
    suspend  fun recuperarListaReceitasApi(areaName:String):List<Receita>
    suspend fun recuperaReceitaPorNome(nomeReceita:String):Receita?
    suspend fun recuperaReceitaId(receita: Receita):Receita?
    suspend fun criarReceita(receita: Receita):Boolean
    suspend fun atualizarReceita(id :Int,receita :Receita):Receita
    suspend fun deletarReceita(receita: Receita):Boolean
    suspend fun  perquisarReceita(pequisa:String):List<Receita>
}