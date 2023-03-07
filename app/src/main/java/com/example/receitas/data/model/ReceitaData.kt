package com.example.receitas.data.model
import android.net.Uri
import com.example.receitas.domain.model.Receita
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId


class ReceitaData :RealmObject {
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


