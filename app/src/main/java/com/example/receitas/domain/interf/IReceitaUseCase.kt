package com.example.receitas.domain.interf

import com.example.receitas.data.model.Dto.Area
import com.example.receitas.data.model.Dto.MealItem
import com.example.receitas.domain.model.Receita
import com.example.receitas.domain.results.ResultConsultas
import com.example.receitas.domain.results.ResultadoOperacaoDb
import com.example.receitas.presentation.model.ReceitaView
import com.example.receitas.presentation.model.ReceitaViewCreate

interface IReceitaUseCase {
    suspend   fun listarReceitar():ResultConsultas
    suspend   fun recuperarReceitasPorArea(areaName:String):List<Receita>
    suspend   fun listarArea():ResultConsultas
    suspend fun getReceitaByName(receita:Receita):ReceitaView
    suspend fun getReceitaById(receita:Receita):ReceitaView
    suspend fun criarReceita(receitaCreate: ReceitaViewCreate):ResultadoOperacaoDb
    suspend fun atualizarReceita(id :Int,receita:Receita):Receita
    suspend fun deletarReceita(receitaView: Receita):Boolean
}