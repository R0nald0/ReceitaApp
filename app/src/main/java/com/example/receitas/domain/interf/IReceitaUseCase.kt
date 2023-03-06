package com.example.receitas.domain.interf

import com.example.receitas.domain.model.Receita
import com.example.receitas.domain.results.ResultConsultasAreas
import com.example.receitas.domain.results.ResultConsultasReceita
import com.example.receitas.domain.results.ResultadoOperacaoDb
import com.example.receitas.presentation.model.ReceitaView
import com.example.receitas.presentation.model.ReceitaViewCreate

interface IReceitaUseCase {
    suspend   fun listarReceita():ResultConsultasReceita
    suspend   fun recuperarReceitasPorArea(areaName:String):List<Receita>
    suspend   fun pesquisarReceitaPorTitulo(tituloPesquisa:String):ResultConsultasReceita
    suspend   fun listarArea():ResultConsultasAreas
    suspend fun getReceitaByName(receita:Receita):ReceitaView
    suspend fun getReceitaById(receita:Receita):ReceitaView
    suspend fun criarReceita(receitaCreate: ReceitaViewCreate):ResultadoOperacaoDb
    suspend fun atualizarReceita(receitaView:ReceitaView):ResultadoOperacaoDb
    suspend fun deletarReceita(receitaView: Receita):ResultadoOperacaoDb


}
