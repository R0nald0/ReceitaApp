package com.example.receitas.data.service.interf

import com.example.receitas.data.model.ReceitaDAO
import org.mongodb.kbson.ObjectId

interface IServiceReceitaDb {
     fun getAll() :List<ReceitaDAO>
     suspend fun searchByName(title :String): List<ReceitaDAO>
     suspend fun post(receita: ReceitaDAO): Boolean
     suspend fun putch(receitaDAO: ReceitaDAO): Boolean
     suspend fun delete(objectId: ObjectId):Boolean
}