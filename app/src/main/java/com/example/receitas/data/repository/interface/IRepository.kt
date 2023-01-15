package com.example.receitas.data.repository.`interface`

import com.example.receitas.domain.model.Receita

interface IRepository {
    suspend fun recuperarListaReceitas():List<Receita>
    suspend fun recuperaReceitaPorId(id :Int):Receita
    suspend fun criarReceita(receita: Receita):Receita
    suspend fun atualizarReceita(id :Int,receita :Receita):Receita
    suspend fun deletarReceita(id :Int):Boolean
}