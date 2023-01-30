package com.example.receitas.presentation.model

import android.os.Parcelable
import com.example.receitas.domain.model.Receita
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReceitaView(
    var id : Int,
    var titulo :String,
    var Imagem : Int,
    var tempo :String,
    var ingredientes: List<String>
):Parcelable



