package com.example.receitas.presentation.model

data class ReceitaView(
    var id : Int,
    var titulo :String,
    var Imagem : Int,
    var tempo :String,
    var ingredientes: List<String>
)



