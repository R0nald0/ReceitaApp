package com.example.receitas.domain.interf

import com.example.receitas.data.model.Dto.Area
import com.example.receitas.data.model.Dto.MealItem
import com.example.receitas.domain.model.Receita
import com.example.receitas.presentation.model.ReceitaView

interface IReceitaUseCase {
    suspend   fun listarReceitar():List<Receita>
    suspend   fun recuperarReceitasPorArea(areaName:String):List<Receita>
    suspend   fun listarArea():List<Area>
    suspend fun getReceitaByName(receita:Receita):ReceitaView
    suspend fun getReceitaById(receita:Receita):ReceitaView
    suspend fun criarReceita(receita: Receita):Boolean
    suspend fun atualizarReceita(id :Int,receita:Receita):Receita
    suspend fun deletarReceita(receitaView: Receita):Boolean
}