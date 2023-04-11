package com.example.receitas.data.local.database

import com.example.receitas.data.local.model.AreaDAO
import com.example.receitas.data.model.ReceitaDAO
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

class RealmeDb {

    private val  config = RealmConfiguration.create(
        schema = setOf(
            ReceitaDAO::class,
            AreaDAO::class
        )
    )
    private val realmDb = Realm.open(config)


        fun getRealm (): Realm{
            return realmDb;
        }




}