package com.example.receitas.data.service

import com.example.receitas.R
import com.example.receitas.data.model.ReceitaData
import com.example.receitas.data.service.interf.IServiceReceita

/*class ReceitaService {
  *//*  private var listReceita = mutableListOf(
        ReceitaData().apply {
              this.idReceita = 0
              this.nome = "escondidinho de Camarão"
              this.image ="${R.drawable.carne1}"
              this.time ="35min"
              this.ingrediente = listOf(
                "1 Kg de camarão branco limpo",
                "Azeite de oliva",
                "2 dentes de alho picados ou amassados",
                "Sal a gosto",
                "1 cebola média picada",
                "1 tomate grande picado",
                "Salsinha e coentro a gosto",
            )as  MutableList

        },
        ReceitaData().apply {
            this.idReceita =1
            this.nome ="panqueca de carne moída"
            this.image = "${ R.drawable.carne2 }"
            this.time = "45min"
            this.ingrediente = listOf(
                    "1 e 1/2 xícara (chá) de farinha de trigo",
                    "1 xícara (chá) de leite",
                    "2 ovos",
                    "4 colheres (sopa) de óleo",
                    "sal á gosto",
                    "300 g de carne moída",
                    "2 colheres (sopa) de cebola picada ou ralada",
                )as  MutableList

        },
        ReceitaData().apply {
              this.idReceita =2
              this.nome = "rocambole de carne moída"
              this.image = "${R.drawable.carne3}"
              this.time ="25min"
              this.ingrediente = listOf(
                "1/2 kg de carne moída",
                "1 pacote de sopa de cebola",
                "presunto fatiado",
                "queijo fatiado",
                "tempero verde",
                "sal a gosto",
            )as  MutableList
        },
        ReceitaData().apply {
              this.idReceita =3
             this.nome = "escondidinho de carne seca"
            this.image = "${R.drawable.carne4}"
            this.time ="50min"
            this.ingrediente =listOf(
                "1 kg de mandioca cozida",
                "1 lata de creme de leite com soro",
                "2 colheres de margarina",
                "1/2 kg de carne-seca dessalgada e cozida",
                "1 cebola média picadinha",
                "4 dentes de alho esmagados",
                "2 tomates sem casca e picados",
                "sal e pimenta a gosto",
                "queijo ralado a gosto",
            )as  MutableList
        },
    )*//*
        fun getAll(): List<ReceitaData> {
         return listReceita
    }

     suspend fun get(id: Int): ReceitaData {
           return  listReceita.get(id)
    }

     suspend fun post(receita: ReceitaData): Boolean {
           if (receita !=null){
                receita.idReceita = listReceita.size
               listReceita.add(receita)
               return true
           }
        return false
    }

     suspend fun putch(id: Int, receitaData: ReceitaData): ReceitaData {
           var  receitaAtualizada = listReceita.set(id,receitaData)
          return  receitaAtualizada
    }

     suspend fun delete(id: Int): Boolean {
            if (id <= listReceita.size){
                listReceita.removeAt(id)
                return true
            }
        return false
    }

}*/

