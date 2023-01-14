package com.example.receitas

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class Receita(
     var titulo :String,
     var Imagem : Int,
     var tempo :String,
     var ingredientes: List<String>
  ) :Parcelable {
}