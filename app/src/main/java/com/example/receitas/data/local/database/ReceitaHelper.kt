package com.example.receitas.data.local.database

import com.example.receitas.data.model.ReceitaDAO
import com.example.receitas.data.service.interf.IServiceReceitaDb
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import org.mongodb.kbson.ObjectId
import javax.inject.Inject


class ReceitaHelper @Inject constructor(
    private val realmDb : Realm
):IServiceReceitaDb{

    override  fun getAll(): List<ReceitaDAO> {
        val lista = realmDb.query<ReceitaDAO>().find()
        return lista
    }

    override suspend fun searchByName(title: String): List<ReceitaDAO> {
         try {
             val receita = realmDb.query<ReceitaDAO>("nome CONTAINS  $0",title).find()
             return receita
         }catch (ex : Exception){
             ex.stackTrace
            return listOf()
         }
    }

    override suspend fun post(receita: ReceitaDAO): Boolean {

         try {
        realmDb.write{
                this.copyToRealm(receita)
            }

            return true

        } catch (e :IllegalArgumentException){
            throw  IllegalArgumentException("Erro : ${e.message} -- ${e.stackTrace}")
        } catch (ex:Exception){
            throw Exception("erro ao salvar o usuario : ${ex.message} -- ${ex.stackTrace} ")
        }
    }


    override suspend fun putch(receitaDAO: ReceitaDAO): Boolean {
          try {
              realmDb.write {
                  val receitaEdit = this.query<ReceitaDAO>("_idRealme == $0",receitaDAO._idRealme).find().first()
                  if (receitaEdit !=null){
                      receitaEdit .nome =receitaDAO.nome
                      receitaEdit .image =receitaDAO.image
                      receitaEdit .ingrediente =receitaDAO.ingrediente
                      receitaEdit .time =receitaDAO.time
                      receitaEdit .instrucao =receitaDAO.instrucao
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
                val  receita = this.query<ReceitaDAO>("_idRealme == $0",objectId).find().first()
                delete(receita)
            }
            return true
        }catch (e:Exception){
            e.printStackTrace()
            throw Exception("erro ao deletar no banco : ${e.message}")
        }
    }
}