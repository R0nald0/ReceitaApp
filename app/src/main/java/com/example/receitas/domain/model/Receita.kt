package com.example.receitas.domain.model


import android.net.Uri
import org.mongodb.kbson.ObjectId

data class Receita(
     var idRealm : ObjectId?,
     var isUserList : Boolean,
     var titulo :String,
     var Imagem : Uri?,
     var ImagemUrl :String,
     var instrucoes :String,
     var tempo :String = "00",
     var ingredientes: String
  )

