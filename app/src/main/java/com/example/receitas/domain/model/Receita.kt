package com.example.receitas.domain.model


import android.net.Uri
import org.mongodb.kbson.ObjectId

data class Receita(
     var idRealm : ObjectId?,
     var id : Int,
     var titulo :String,
     var Imagem : String,
     var ImagemUrl :String,
     var instrucoes :String,
     var tempo :String = "00",
     var ingredientes: String
  )

