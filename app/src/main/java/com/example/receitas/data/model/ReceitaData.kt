package com.example.receitas.data.model
import com.example.receitas.domain.model.Receita
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId


class ReceitaData :RealmObject {
     @PrimaryKey
     var _idRealme : ObjectId = ObjectId()
     var idReceita: Int = 0
     var nome :String =""
     var image : Int =0
     var imageLink :String =""
     var time :String =""
     var instrucao :String = ""
     var ingrediente: MutableList<String> = mutableListOf()
}


