package com.example.receitas.data.local.database

import com.example.receitas.data.model.ReceitaData
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

class RealmeDb {

    private val  config = RealmConfiguration.create(schema = setOf(ReceitaData::class))
    private val realmDb = Realm.open(config)


        fun getRealm (): Realm{
            return realmDb;
        }




}