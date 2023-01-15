package com.example.receitas.data.repository

import com.example.receitas.data.model.ReceitaData
import com.example.receitas.data.model.convertToReceita
import com.example.receitas.data.repository.`interface`.IRepository
import com.example.receitas.data.service.`interface`.IServiceReceita
import com.example.receitas.domain.model.Receita

class  ReceitaRepository(private val service :IServiceReceita) :IRepository{

    override suspend fun recuperarListaReceitas(): List<Receita> {
          val listaReceitaApi = service.getAll()
        if (listaReceitaApi !=null){
             val listaReceitas =listaReceitaApi.map {
                  it.convertToReceita()
             }

            return  listaReceitas
        }
        return emptyList()
    }

    override suspend fun recuperaReceitaPorId(id: Int): Receita {
          val receitaData = service.get(id)
        return  receitaData.convertToReceita().copy()
    }

    override  suspend fun criarReceita(receita: Receita): Receita {
        val receitaData = ReceitaData(
            receita.id,receita.titulo,receita.Imagem,receita.tempo,receita.ingredientes
        )
        return   service.post(receitaData).convertToReceita().copy()
    }

    override  suspend fun atualizarReceita(id: Int, receita: Receita): Receita {
         val receitaData = ReceitaData(
             receita.id,receita.titulo,receita.Imagem,receita.tempo,receita.ingredientes
         )
          var receitaAtualizado = service.putch(id,receitaData)
         return  receitaData.convertToReceita().copy()
     }

    override suspend fun deletarReceita(id: Int) :Boolean {
          return service.delete(id)
    }

}