package com.example.receitas.mapReceita

import android.net.Uri
import com.example.receitas.data.model.Dto.MealItem
import com.example.receitas.data.model.ReceitaData
import com.example.receitas.domain.model.Receita
import com.example.receitas.presentation.model.ReceitaView
import com.example.receitas.presentation.model.ReceitaViewCreate
import org.mongodb.kbson.ObjectId


object MapReceita {

    fun convertMealListItemToReceitaData(mealItem: MealItem) :ReceitaData{
         return  ReceitaData().apply {
             this.nome = mealItem.strMeal
             this.imageLink =mealItem.strMealThumb
             this.instrucao = "${mealItem.strInstructions}"
             this.ingrediente +="${mealItem.strIngredient1}\n" +
                     "${mealItem.strIngredient2}\n" +
                     "${ mealItem.strIngredient3}\n" +
                     "${mealItem.strIngredient4}\n" +
                     "${mealItem.strIngredient6}\n" +
                     "${mealItem.strIngredient7}\n" +
                     "${ mealItem.strIngredient8}\n"+
                     "${ mealItem.strIngredient9}\n"+
                     "${ mealItem.strIngredient10}\n"+
                     "${ mealItem.strIngredient11}\n"+
                     "${ mealItem.strIngredient12}\n"+
                     "${ mealItem.strIngredient14}\n"+
                     "${ mealItem.strIngredient15}\n"
         }
    }
    fun rceitaToReceitaData(receita: Receita):ReceitaData{
         return ReceitaData().apply {
             this._idRealme = receita.idRealm!!
             this.idReceita = receita.id
             this.nome = receita.titulo
             this.time = receita.tempo
             this.instrucao = receita.instrucoes
             this.image =receita.Imagem
             this.ingrediente = receita.ingredientes
         }
    }

    fun receitaDataToReceita(receitaData: ReceitaData)=Receita (
        idRealm = receitaData._idRealme,
        id = receitaData.idReceita,
        titulo = receitaData.nome,
        Imagem = receitaData.image,
        ImagemUrl= receitaData.imageLink,
        instrucoes= receitaData.instrucao,
        tempo = receitaData.time,
        ingredientes = receitaData.ingrediente
        )

    fun receitaViewToReceita(receitaView: ReceitaView)= Receita(
        idRealm = ObjectId.invoke("${receitaView.idRealm}"),
        id =receitaView.id,
        titulo =receitaView.titulo,
        Imagem =receitaView.Imagem,
        ImagemUrl= receitaView.ImageUrl,
        tempo = receitaView.tempo,
        instrucoes = receitaView.instrucao,
        ingredientes =receitaView.ingredientes
    )

    fun receitaViewCreateToReceita(receitaViewCreate: ReceitaViewCreate)= Receita(
        idRealm = ObjectId(),
        id =0,
        titulo =receitaViewCreate.titulo,
        Imagem =receitaViewCreate.Imagem,
        ImagemUrl="",
        tempo = receitaViewCreate.tempo,
        instrucoes = receitaViewCreate.instrucao,
        ingredientes =receitaViewCreate.ingredientes

    )

    fun receitaToReceitaView(receita: Receita)=ReceitaView (
        idRealm = receita.idRealm?.toHexString(),
        id =receita.id,
        titulo =receita.titulo,
        Imagem = receita.Imagem,
        ImageUrl =receita.ImagemUrl,
        tempo = receita.tempo,
        instrucao = receita.instrucoes,
        ingredientes =receita.ingredientes
    )
    fun toListReceitaView(listReceita: List<Receita>): List<ReceitaView> {
          val list = listReceita.map {
              receitaToReceitaView(it)
         }
        return list
    }

}