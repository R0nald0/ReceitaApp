package com.example.receitas.domain.useCase

import android.util.Log
import com.example.receitas.constant.Const
import com.example.receitas.data.model.Dto.Area
import com.example.receitas.data.model.Dto.MealItem
import com.example.receitas.data.repository.interf.IRepository
import com.example.receitas.domain.model.Receita
import com.example.receitas.domain.interf.IReceitaUseCase
import com.example.receitas.mapReceita.MapReceita
import com.example.receitas.presentation.model.ReceitaView


import javax.inject.Inject

class ReceitaUseCase @Inject constructor(
    private val repository: IRepository
    ) : IReceitaUseCase {

    override suspend fun listarArea(): List<Area> {
          try {
              return repository.recuperarListaArea()
          }catch(ex:Exception){
               throw Exception("erro a :${ex.message}")
          }
    }
    override suspend fun listarReceitar(): List<Receita> {
         try {
             val listReceita = repository.recuperarListaReceitas()
             return listReceita
         }catch (ex:Exception){
             Log.i("ERRO", "listarReceitar: ${ex.message}")
             throw Exception("Erro ao recuperar lista  ${ex.message} -- ${ex.stackTrace}" )
         }
    }

    override suspend fun recuperarReceitasPorArea(areaName: String): List<Receita> {
          try {
              val listaReceita = repository.recuperarReceitasPorArea(areaName)
              return  listaReceita
          }catch (ex:Exception){
               Const.exibilog("eerro : ${ex.message}}")
          }

        return  listOf()
    }

    /*
    * criar classe de estado para gerenciar estado da lista
    *
    * */
    override suspend fun getReceitaByName(receita: Receita): ReceitaView {
        try {
            val receitaViwe =  repository.recuperaReceitaPorNome(receita.titulo)
            if (receitaViwe !=null){
                return  MapReceita.receitaToReceitaView(receitaViwe)
            }else{
                return  this.getReceitaById(receita)
            }
            return  ReceitaView("",-1,"",1,"", "","", emptyList())

        }catch (ex:Exception){
            throw Exception("Errro ao recuperar Receita :${ex.message}")
        }
    }
    override suspend fun getReceitaById(receita: Receita): ReceitaView {
        try {
          val receitaViwe =  repository.recuperaReceitaId(receita)
             if (receitaViwe !=null){
                 return  MapReceita.receitaToReceitaView(receitaViwe)
             }
            return  ReceitaView("",-1,"",1,"", "","", emptyList())

        }catch (ex:Exception){
            throw Exception("Errro ao recuperar Receita :${ex.message}")
        }
    }
    override suspend fun criarReceita(receita: Receita): Boolean {
        try {
            val receitaCriada = repository.criarReceita(receita)
            Log.i("INFO_", "criarReceita: ${receita}")
            return  receitaCriada
        }catch (ex:Exception){
            throw Exception("Erro ao criar Receita: ${ex.message} -- ${ex.stackTrace} ")
        }
    }
    override suspend fun atualizarReceita(id: Int, receita: Receita): Receita {
             try {
                 val receitaAtualizada = repository.atualizarReceita(id,receita)
                 Log.i("Receita-", "atualizarReceita: ${receitaAtualizada.titulo}")
                 return  receitaAtualizada
             }catch (ex :Exception){
                 throw Exception("Erro ao Atualizar Receita")
             }

    }

    override suspend fun deletarReceita(receita: Receita): Boolean {

        try {
            return repository.deletarReceita(receita)
        } catch (ex: Exception) {
            throw Exception("Erro ao deletar Receita: ${ex.message}")
        }
    }

}