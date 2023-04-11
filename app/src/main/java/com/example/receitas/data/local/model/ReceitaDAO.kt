package com.example.receitas.data.local.model


import com.example.receitas.data.model.ReceitaDAO
import org.mongodb.kbson.BsonObjectId
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class ReceitaDAO {
    @PrimaryKey
    var _idRealme : ObjectId = BsonObjectId()
    var isUserList : Boolean = true
    var nome :String =""
    var image : String =""
    var imageLink :String =""
    var time :String =""
    var instrucao :String = ""
    var ingrediente: String =""
}

fun ReceitaDAO.toData() = ReceitaDAO().apply {
  this.nome = nome
}