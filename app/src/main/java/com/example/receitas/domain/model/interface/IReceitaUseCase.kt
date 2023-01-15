package com.example.receitas.domain.model.`interface`

import com.example.receitas.domain.model.Receita

interface IReceitaUseCase {
    suspend fun listarReceitar():List<Receita>?
    suspend fun recuperaReceitaPorId(id :Int):Receita
    suspend fun criarReceita(receita: Receita):Receita
    suspend fun atualizarReceita(id :Int,receita:Receita):Receita
    suspend fun deletarReceita(id :Int):Boolean
}