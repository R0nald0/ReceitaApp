package com.example.receitas.data.service

import com.example.receitas.R
import com.example.receitas.data.model.ReceitaData
import com.example.receitas.data.service.`interface`.IServiceReceita
import com.example.receitas.domain.model.Receita

class ReceitaService() :IServiceReceita{
    var listReceita = mutableListOf<ReceitaData>(
        ReceitaData(0,"escondidinho de Camarão", R.drawable.carne1,"35min",
            listOf(
                "1 Kg de camarão branco limpo",
                "Azeite de oliva",
                "2 dentes de alho picados ou amassados",
                "Sal a gosto",
                "1 cebola média picada",
                "1 tomate grande picado",
                "Salsinha e coentro a gosto",
            )
        ),
        ReceitaData(1,"panqueca de carne moída", R.drawable.carne2,"45min",
            listOf(
                "1 e 1/2 xícara (chá) de farinha de trigo",
                "1 xícara (chá) de leite",
                "2 ovos",
                "4 colheres (sopa) de óleo",
                "sal á gosto",
                "300 g de carne moída",
                "2 colheres (sopa) de cebola picada ou ralada",
            )
        ),
        ReceitaData(2,"rocambole de carne moída", R.drawable.carne3,"25min",
            listOf(
                "1/2 kg de carne moída",
                "1 pacote de sopa de cebola",
                "presunto fatiado",
                "queijo fatiado",
                "tempero verde",
                "sal a gosto",
            )
        ),
        ReceitaData(3,"escondidinho de carne seca", R.drawable.carne4,"50min",
            listOf(
                "1 kg de mandioca cozida",
                "1 lata de creme de leite com soro",
                "2 colheres de margarina",
                "1/2 kg de carne-seca dessalgada e cozida",
                "1 cebola média picadinha",
                "4 dentes de alho esmagados",
                "2 tomates sem casca e picados",
                "sal e pimenta a gosto",
                "queijo ralado a gosto",
            )
        ),
    )
    override  fun getAll(): List<ReceitaData> {
         return listReceita
    }

    override suspend fun get(id: Int): ReceitaData {
           return  listReceita.get(id)
    }

    override suspend fun post(receita: ReceitaData): ReceitaData {
           if (receita !=null){
               listReceita.add(receita)
               return receita
           }
        return ReceitaData(0,"",0,"", listOf())
    }

    override suspend fun putch(id: Int, receitaData: ReceitaData): ReceitaData {
           var  receitaAtualizada = listReceita.set(id,receitaData)
          return  receitaAtualizada
    }

    override suspend fun delete(id: Int): Boolean {
            if (id <= listReceita.size){
                listReceita.removeAt(id)
                return true
            }
        return false
    }

}
