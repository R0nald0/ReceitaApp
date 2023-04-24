package com.example.receitas.domain.interf

import com.example.receitas.domain.model.Receita
import com.example.receitas.domain.results.ResultConsultasAreas
import com.example.receitas.domain.results.ResultConsultasReceita
import com.example.receitas.domain.results.ResultadoOperacaoDb
import com.example.receitas.domain.results.VerificaCampos
import com.example.receitas.presentation.model.ReceitaView
import com.example.receitas.presentation.model.ReceitaViewCreate

interface IReceitaUseCase {
    fun verificarCampos(receitaView: ReceitaViewCreate) :VerificaCampos
    suspend fun addReceitaToUserList(receitaView: ReceitaView):ResultadoOperacaoDb
    suspend   fun listarReceita():ResultConsultasReceita
    suspend   fun getReceitaByAreaName(areaName:String): List<ReceitaView>
    suspend   fun pesquisarReceitaPorTitulo(tituloPesquisa:String):ResultConsultasReceita
    suspend   fun listarArea():ResultConsultasAreas
    suspend fun getReceitaByName(receitaName:String):Result<ReceitaView>
    suspend fun getReceitaById(receita:Receita):ReceitaView
    suspend fun criarReceita(receita: Receita):ResultadoOperacaoDb
    suspend fun atualizarReceita(receitaView:ReceitaView):ResultadoOperacaoDb
    suspend fun deletarReceita(receita: Receita):ResultadoOperacaoDb
    suspend fun getListBanner():ResultConsultasReceita


}
