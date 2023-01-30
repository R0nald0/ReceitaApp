package com.example.receitas.domain.interf

import com.example.receitas.domain.model.Receita
import com.example.receitas.presentation.model.ReceitaView

interface IReceitaUseCase {
    suspend fun listarReceitar():List<Receita>?
    suspend fun recuperaReceitaPorId(id :Int):ReceitaView
    suspend fun criarReceita(receita: Receita):ReceitaView
    suspend fun atualizarReceita(id :Int,receita:Receita):Receita
    suspend fun deletarReceita(id :Int):Boolean
}