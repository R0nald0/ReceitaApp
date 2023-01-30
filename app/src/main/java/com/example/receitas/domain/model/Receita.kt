package com.example.receitas.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class Receita(
     var id : Int,
     var titulo :String,
     var Imagem : Int,
     var tempo :String,
     var ingredientes: List<String>
  ) :Parcelable {

}

