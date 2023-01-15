package com.example.receitas.data.model

import com.example.receitas.domain.model.Receita


data class ReceitaData(
    var id: Int,
    var nome :String,
    var image : Int,
    var time :String,
    var ingrediente: List<String>) {

}

 fun ReceitaData.convertToReceita() = Receita(
       id = this.id,
      titulo =this.nome,
      Imagem =this.image,
      tempo  = this.time,
      ingredientes =this.ingrediente
 )
