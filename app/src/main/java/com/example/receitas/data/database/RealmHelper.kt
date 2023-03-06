package com.example.receitas.data.database

import android.util.Log
import com.example.receitas.shared.constant.Const
import com.example.receitas.data.model.ReceitaData
import com.example.receitas.data.service.interf.IServiceReceita
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults
import org.mongodb.kbson.ObjectId


class RealmHelper :IServiceReceita{
   private val  config = RealmConfiguration.create(schema = setOf(ReceitaData::class))
   private val realmDb = Realm.open(config)

    override   fun getAll(): List<ReceitaData> {
        val lista = realmDb.query<ReceitaData>().find()
        return lista
    }

    override suspend fun searchByName(title: String): List<ReceitaData> {
         try {
             val receita = realmDb.query<ReceitaData>("nome CONTAINS  $0",title).find()
             return receita
         }catch (ex : Exception){
            return listOf<ReceitaData>()
         }
    }

    override suspend fun post(receita: ReceitaData): Boolean {

         try {
            realmDb.write{
                this.copyToRealm(
                    ReceitaData().apply {
                        this.time = receita.time
                        this.image =receita.image
                        this.imageLink =receita.imageLink
                        this.ingrediente = receita.ingrediente
                        this.instrucao =receita.instrucao
                        this.nome =receita.nome
                    }
                )

            }
            return true

        } catch (e :IllegalArgumentException){
            throw  IllegalArgumentException("Erro : ${e.message} -- ${e.stackTrace}")
        } catch (ex:Exception){
            throw Exception("erro ao salvar o usuario : ${ex.message} -- ${ex.stackTrace} ")
        }
    }

    override suspend fun putch(receitaData: ReceitaData): Boolean {
          try {
              realmDb.write {
                  val receitaEdit = this.query<ReceitaData>("_idRealme == $0",receitaData._idRealme).find().first()
                  if (receitaEdit !=null){
                      receitaEdit .nome =receitaData.nome
                      receitaEdit .image =receitaData.image
                      receitaEdit .ingrediente =receitaData.ingrediente
                      receitaEdit .time =receitaData.time
                      receitaEdit .instrucao =receitaData.instrucao
                  }
              }
              return true
          }catch (ex:Exception){
              throw Exception("${ex.message}")
          }
    }

    override suspend fun delete(objectId: ObjectId): Boolean {
        try {
            realmDb.write {
                val  receita = this.query<ReceitaData>("_idRealme == $0",objectId).find().first()
                delete(receita)
            }
            return true
        }catch (e:Exception){
            throw Exception("erro ao deletar no banco : ${e.message} -- ${e.stackTrace}" )
        }
    }
}