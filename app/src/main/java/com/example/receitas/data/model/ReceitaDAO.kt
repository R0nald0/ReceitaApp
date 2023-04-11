package com.example.receitas.data.model
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId


class ReceitaDAO :RealmObject {
     @PrimaryKey
     var _idRealme : ObjectId = ObjectId()
     var isUserList : Boolean = true
     var nome :String =""
     var image : String =""
     var imageLink :String =""
     var time :String =""
     var instrucao :String = ""
     var ingrediente: String =""
}


