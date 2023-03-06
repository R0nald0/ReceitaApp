package com.example.receitas.data.service.interf

import com.example.receitas.data.model.ReceitaData
import org.mongodb.kbson.ObjectId

interface IServiceReceita {
      fun getAll() :List<ReceitaData>

     suspend fun searchByName(title :String): List<ReceitaData>
     suspend fun post(receita: ReceitaData): Boolean
     suspend fun putch(receitaData: ReceitaData): Boolean
     suspend fun delete(objectId: ObjectId):Boolean
}