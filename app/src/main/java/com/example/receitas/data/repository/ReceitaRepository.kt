package com.example.receitas.data.repository

import com.example.receitas.data.model.ReceitaData
import com.example.receitas.data.model.convertToReceita
import com.example.receitas.data.repository.interf.IRepository
import com.example.receitas.data.service.interf.IServiceReceita
import com.example.receitas.domain.model.Receita
import com.example.receitas.mapReceita.MapReceita
import javax.inject.Inject

class  ReceitaRepository @Inject constructor(
      private val service :IServiceReceita
    ) :IRepository{

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
         val receitaData = MapReceita.rceitaToReceitaData(receita)
        return  service.post(receitaData).convertToReceita().copy()
    }

    override  suspend fun atualizarReceita(id: Int, receita: Receita): Receita {
          val receitaAtualizado = service.putch(id, MapReceita.rceitaToReceitaData(receita))
         return  receitaAtualizado.convertToReceita().copy()
     }

    override suspend fun deletarReceita(id: Int) :Boolean {
          return service.delete(id)
    }

}