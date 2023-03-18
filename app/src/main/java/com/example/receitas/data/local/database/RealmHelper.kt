package com.example.receitas.data.local.database

import com.example.receitas.data.model.ReceitaData
import com.example.receitas.data.service.interf.IServiceReceitaDb
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import org.mongodb.kbson.ObjectId
import javax.inject.Inject


class RealmHelper @Inject constructor(
    private val realmDb : Realm
):IServiceReceitaDb{

    override  fun getAll(): List<ReceitaData> {

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
                        this.isUserList = receita.isUserList
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