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
        val lista :RealmResults<ReceitaData> = realmDb.query<ReceitaData>().find()
         return lista
    }

    override suspend fun get(objectId: ObjectId): ReceitaData {
        try {
            var receita = ReceitaData()
      realmDb.write {
             receita  = this.query<ReceitaData>("_idRealme==$0",objectId).find().first()
         }
            return receita
        }catch (e:Exception){
            throw  Exception("erro ao buscar ${e.message}"  )
        }
    }

    override suspend fun post(receita: ReceitaData): Boolean {

         try {
            realmDb.write{
                 copyToRealm(receita)
                Log.i("Receita-", "RECEita db: ${receita.nome}")
            }
            return true

        } catch (e :IllegalArgumentException){
            throw  IllegalArgumentException("Erro : ${e.message} -- ${e.stackTrace}")
        } catch (ex:Exception){
            throw Exception("erro ao salvar o usuario : ${ex.message} -- ${ex.stackTrace} ")
        }
    }

    override suspend fun putch(id: Int, receitaData: ReceitaData): ReceitaData {
        TODO("Not yet implemented")
    }

    override suspend fun delete(objectId: ObjectId): Boolean {
        try {
            realmDb.write {
                val  receita = this.query<ReceitaData>("_idRealme == $0",objectId).find().first()
                Const.exibilog("id receita deletado :${receita._idRealme}")
                delete(receita)
            }
            return true
        }catch (e:Exception){
            throw Exception("erro ao deletar no banco : ${e.message} -- ${e.stackTrace}" )
        }
    }
}