package com.example.receitas.presentation.model




import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize



@Parcelize
data class ReceitaView(
    var idRealm : String? ,
    var id : Int,
    var titulo :String,
    var Imagem : String,
    var ImageUrl : String,
    var instrucao : String,
    var tempo :String,
    var ingredientes: String
):Parcelable

data class ReceitaViewCreate(
    var titulo :String,
    var Imagem : String,
    var tempo :String,
    var instrucao :String,
    var ingredientes: String
)





