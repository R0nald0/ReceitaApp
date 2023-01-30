package com.example.receitas.data.model

import com.example.receitas.domain.model.Receita
import org.mongodb.kbson.ObjectId
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey



 class ReceitaData :RealmObject {
     @PrimaryKey
     var _idRealme :String =ObjectId().toHexString()
     var idReceita: Int = 0
     var nome :String =""
     var image : Int =0
     var time :String =""
     lateinit var ingrediente: List<String>
}

 fun ReceitaData.convertToReceita() = Receita(
       id = this.idReceita,
      titulo =this.nome,
      Imagem =this.image,
      tempo  = this.time,
      ingredientes =this.ingrediente
 )
