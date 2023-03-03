package com.example.receitas.domain.useCase

import android.net.Uri
import android.util.Log
import com.example.receitas.R
import com.example.receitas.shared.constant.Const
import com.example.receitas.data.model.Dto.Area
import com.example.receitas.data.model.Dto.MealItem
import com.example.receitas.data.repository.interf.IRepository
import com.example.receitas.domain.model.Receita
import com.example.receitas.domain.interf.IReceitaUseCase
import com.example.receitas.domain.results.ResultConsultas
import com.example.receitas.domain.results.ResultadoOperacaoDb
import com.example.receitas.mapReceita.MapReceita
import com.example.receitas.presentation.model.ReceitaView
import com.example.receitas.presentation.model.ReceitaViewCreate


import javax.inject.Inject

class ReceitaUseCase @Inject constructor(
    private val repository: IRepository
    ) : IReceitaUseCase {

    override suspend fun listarArea():ResultConsultas {
          try {
              val areas  = repository.recuperarListaArea()
              return   ResultConsultas(true,"Sucesso ao carregar lista de areas",areas)

          }catch(ex:Exception){
              ResultConsultas(false,"erro ao carregar lista de areas", listOf())
               throw Exception("erro a :${ex.message}")
          }
    }
    override suspend fun listarReceitar(): ResultConsultas {
         try {
             val listReceita = repository.recuperarListaReceitas()
             val listaReceitaview = MapReceita.toListReceitaView(listReceita)
             return  ResultConsultas(
                 true,
                 "Lista carregada com sucesso",
                 listaReceitaview
             )
         }catch (ex:Exception){
             throw Exception("Erro ao recuperar lista  ${ex.message} -- ${ex.stackTrace}" )
         }
    }
    override suspend fun recuperarReceitasPorArea(areaName: String): List<Receita> {
          try {
              val listaReceita = repository.recuperarReceitasPorArea(areaName)
              return  listaReceita
          }catch (ex:Exception){
            ex.stackTrace
              return listOf()
          }
    }
    override suspend fun getReceitaByName(receita: Receita): ReceitaView {
        try {
            val receitaViwe =  repository.recuperaReceitaPorNome(receita.titulo)
            if (receitaViwe !=null){
                return  MapReceita.receitaToReceitaView(receitaViwe)
            }else{
                return  this.getReceitaById(receita)
            }

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
            return  ReceitaView("",-1,"", "","", "","", "")

        }catch (ex:Exception){
            throw Exception("Errro ao recuperar Receita :${ex.message}")
        }
    }
    override suspend fun criarReceita(receitaCreate: ReceitaViewCreate): ResultadoOperacaoDb {
        try {


            if ( receitaCreate.titulo.isEmpty())
                return ResultadoOperacaoDb(false,"preenchos campos o nome")

            if (receitaCreate.ingredientes.isNullOrEmpty())
                return ResultadoOperacaoDb(false,"preencha os ingredientes")
            if (receitaCreate.tempo.isEmpty())
                return ResultadoOperacaoDb(false,"preenchas o tempo")
            if (receitaCreate.instrucao.isEmpty())
                return ResultadoOperacaoDb(false,"preenchos as instrucoes")




            val receita = MapReceita.receitaViewCreateToReceita(receitaCreate)
            val receitaCriada = repository.criarReceita(receita)
            return ResultadoOperacaoDb(
                 receitaCriada,
                 "salvo com sucesso"
             )

        }catch (ex:Exception){
           return ResultadoOperacaoDb(
                false,"erro: ${ex.message} -- ${ex.stackTrace}"
            )
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
        return try {
            repository.deletarReceita(receita)
        } catch (ex: Exception) {
            ex.printStackTrace()
            false
        }
    }

}