package com.example.receitas.data.service.`interface`

import com.example.receitas.data.model.ReceitaData

interface IServiceReceita {
     fun getAll() :List<ReceitaData>
     suspend fun get(id :Int): ReceitaData
     suspend fun post(receita: ReceitaData): ReceitaData
     suspend fun putch(id :Int,receitaData: ReceitaData): ReceitaData
     suspend fun delete(id :Int):Boolean
}