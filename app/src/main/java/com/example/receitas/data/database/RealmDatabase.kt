package com.example.receitas.data.database

import com.example.receitas.data.model.ReceitaData
import com.example.receitas.data.service.interf.IServiceReceita
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import javax.inject.Singleton

@Singleton
class RealmDatabase :IServiceReceita{
    val config = RealmConfiguration.create(setOf(ReceitaData::class))
    val realmDb = Realm.open(config)

    override fun getAll(): List<ReceitaData> {
        TODO("Not yet implemented")
    }

    override suspend fun get(id: Int): ReceitaData {
        TODO("Not yet implemented")
    }

    override suspend fun post(receita: ReceitaData): ReceitaData {
        TODO("Not yet implemented")
    }

    override suspend fun putch(id: Int, receitaData: ReceitaData): ReceitaData {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: Int): Boolean {
        TODO("Not yet implemented")
    }
}