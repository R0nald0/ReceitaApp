package com.example.receitas.mapReceita

import com.example.receitas.data.model.ReceitaData
import com.example.receitas.domain.model.Receita
import com.example.receitas.presentation.model.ReceitaView


object MapReceita {
    fun rceitaToReceitaData(receita: Receita):ReceitaData{
         return ReceitaData().apply {
             this.idReceita = receita.id
             this.nome = receita.titulo
             this.time = receita.tempo
             this.image =receita.Imagem
             this.ingrediente =receita.ingredientes
         }
    }

    fun receitaDataToReceita(receitaData: ReceitaData)=Receita (
        id = receitaData.idReceita,
        titulo = receitaData.nome,
        Imagem = receitaData.image,
        tempo = receitaData.time,
        ingredientes = receitaData.ingrediente
        )

    fun receitaViewToReceita(receitaView: ReceitaView)= Receita(
        id =receitaView.id,
        titulo =receitaView.titulo,
        Imagem =receitaView.Imagem,
        tempo = receitaView.tempo,
        ingredientes =receitaView.ingredientes
    )
    fun receitaToReceitaView(receita: Receita)=ReceitaView (
        id =receita.id,
        titulo =receita.titulo,
        Imagem =receita.Imagem,
        tempo = receita.tempo,
        ingredientes =receita.ingredientes
    )
    fun toListReceitaView(listReceita: List<Receita>): List<ReceitaView> {
          val list = listReceita.map {
              receitaToReceitaView(it)
         }
        return list
    }

}