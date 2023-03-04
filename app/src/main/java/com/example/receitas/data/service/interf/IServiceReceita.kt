package com.example.receitas.data.service.interf

import com.example.receitas.data.model.ReceitaData
import org.mongodb.kbson.ObjectId

interface IServiceReceita {
      fun getAll() :List<ReceitaData>

     suspend fun searchByName(title :String): List<ReceitaData>
     suspend fun post(receita: ReceitaData): Boolean
     suspend fun putch(id :Int,receitaData: ReceitaData): ReceitaData
     suspend fun delete(uuid: ObjectId):Boolean
}