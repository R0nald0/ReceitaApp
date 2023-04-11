package com.example.receitas.data.local.database

import com.example.receitas.data.local.model.AreaDAO
import com.example.receitas.shared.constant.Const
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import org.mongodb.kbson.BsonObjectId
import org.mongodb.kbson.ObjectId
import javax.inject.Inject

class AreaHelper @Inject constructor(
    val areaDb : Realm
){
     fun getAll(): List<AreaDAO> {
       return areaDb.query<AreaDAO>().find()
    }

     suspend fun searchByName(id: BsonObjectId): AreaDAO {
        TODO("Not yet implemented")
    }

     suspend fun post(areas: List<AreaDAO>): Boolean {
         try {
             areas.forEach { area ->
                     areaDb.write {
                         this.copyToRealm(area)
                     }
                  }
             return true
         } catch (e :IllegalArgumentException){
             throw  IllegalArgumentException("Erro : ${e.message} -- ${e.stackTrace}")
         } catch (ex:Exception){
             throw Exception("erro ao salvar  : ${ex.message} -- ${ex.stackTrace} ")
         }
    }

     suspend fun putch(): Boolean {
        TODO("Not yet implemented")
    }

     suspend fun delete(objectId: ObjectId): Boolean {
        TODO("Not yet implemented")
    }
}