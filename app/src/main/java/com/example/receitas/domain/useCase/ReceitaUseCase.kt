package com.example.receitas.domain.useCase

import android.net.Uri
import com.example.receitas.data.repository.interf.IRepository
import com.example.receitas.domain.model.Receita
import com.example.receitas.domain.interf.IReceitaUseCase

import com.example.receitas.domain.results.ResultConsultasAreas
import com.example.receitas.domain.results.ResultConsultasReceita
import com.example.receitas.domain.results.ResultadoOperacaoDb
import com.example.receitas.domain.results.VerificaCampos
import com.example.receitas.mapReceita.MapReceita
import com.example.receitas.presentation.model.ReceitaView
import com.example.receitas.presentation.model.ReceitaViewCreate


import javax.inject.Inject

class ReceitaUseCase @Inject constructor(
    private val repository: IRepository
    ) : IReceitaUseCase {

    override suspend fun listarArea():ResultConsultasAreas {
          try {
              val areas  = repository.recuperarListaArea()
                if(areas.isNotEmpty()){
                    return   ResultConsultasAreas(true,"Sucesso ao carregar lista de areas",areas)
                }
              return   ResultConsultasAreas(true,"Lista vazia",areas)

          }catch(ex:Exception){
              ex.stackTrace
             return ResultConsultasAreas(false,"erro ao carregar lista de areas", listOf())
          }
    }
    override suspend fun listarReceita(): ResultConsultasReceita {
         try {
             val listReceita = repository.getUserListReceitasDb()

             val listaReceitaview = MapReceita.toListReceitaView(listReceita)
            if(listaReceitaview.isNotEmpty()){
                return  ResultConsultasReceita(
                    true,
                    "Lista carregada com sucesso",
                    listaReceitaview
                )
            }else{
                return  ResultConsultasReceita(
                    true,
                    "Lista vazia",
                    listaReceitaview
                )
            }

         }catch (ex:Exception){
             ex.stackTrace
             return  ResultConsultasReceita(
                 false,
                 "Erro ao recuperar lista",
                 listOf()
             )
         }
    }
    override suspend fun getReceitaByAreaName(areaName: String): List<ReceitaView> {
          try {
              val listaReceita = repository.recuperarReceitasPorArea(areaName)
              val listaReceitaView = MapReceita.toListReceitaView(listaReceita)
              return  listaReceitaView
          }catch (ex:Exception){
            ex.stackTrace
              return listOf()
          }
    }
    override suspend fun getReceitaByName(receitaName: String): Result<ReceitaView> {
        try {
            val receitaView =  repository.recuperaReceitaPorNome(receitaName)

            if (receitaView !=null){
                   val retornoReceita =  MapReceita.receitaToReceitaView(receitaView)
                return  Result.success(retornoReceita)
            }else{
                return  Result.success(
                    ReceitaView("",false,"null", Uri.EMPTY, "","", "","")
                )
            }

        }catch (ex:Exception){
             ex.stackTrace
             return  Result.failure(Exception("Errro ao recuperar Receita"))
        }
    }
    override suspend fun getReceitaById(receita: Receita): ReceitaView {
        try {
          val receitaView =  repository.recuperaReceitaId(receita)
             if (receitaView !=null){
                 return  MapReceita.receitaToReceitaView(receitaView)
             }
            return  ReceitaView("",false,"null", Uri.EMPTY, "","", "","")

        }catch (ex:Exception){
            throw Exception("Erro ao recuperar Receita :${ex.message}")
        }
    }
    override suspend fun criarReceita(receita: Receita): ResultadoOperacaoDb {
        try {

            if ( receita.titulo.isEmpty())
                return ResultadoOperacaoDb(false,"preenchos campos o nome")

            if (receita.ingredientes.isEmpty())
                return ResultadoOperacaoDb(false,"preencha os ingredientes")
            if (receita.tempo.isEmpty())
                receita.tempo ="00"
            if (receita.instrucoes.isEmpty())
                return ResultadoOperacaoDb(false,"preencha as instrucoes")

            val receitaCriada = repository.criarReceita(receita)
            return ResultadoOperacaoDb(
                   receitaCriada,
                 "salvo com sucesso"
             )

        }catch (ex:Exception){
            ex.stackTrace
           return ResultadoOperacaoDb(
                false,"erro: ${ex.message} -- ${ex.stackTrace}"
            )
        }
    }
    override suspend fun atualizarReceita(receitaView: ReceitaView): ResultadoOperacaoDb {
             try {
                 val receita = MapReceita.receitaViewToReceita(receitaView)
                 val receitaAtualizada = repository.atualizarReceita(receita)
                 return  ResultadoOperacaoDb(receitaAtualizada,"Atualizado")
             }catch (ex :Exception){
                 ex.printStackTrace()
                 return  ResultadoOperacaoDb(false,"Erro ao Atualizar Receita ${ex.message}")
             }
    }
    override suspend fun deletarReceita(receita: Receita): ResultadoOperacaoDb {
        return try {
         val result =  repository.deletarReceita(receita)
            ResultadoOperacaoDb(result,"Deletado com sucesso")
        } catch (ex: Exception) {
            ex.printStackTrace()
          return  ResultadoOperacaoDb(false,"erro ao deletar")
        }
    }
    override suspend fun pesquisarReceitaPorTitulo(tituloPesquisa: String): ResultConsultasReceita {
        try {
            val result =repository.perquisarReceita(tituloPesquisa)
               val  listaReceitaView = MapReceita.toListReceitaView(result)
                return ResultConsultasReceita(
                    true,"Lista Carreagada com successo",listaReceitaView
                )

    }catch (ex :Exception){
        ex.stackTrace
      return  ResultConsultasReceita(false,"erro ${ ex.message}", listOf())
    }}

    override fun verificarCampos(receitaView: ReceitaViewCreate): VerificaCampos {
         val verificaCampos =VerificaCampos(receitaView)
        return verificaCampos
    }
    override suspend fun addReceitaToUserList(receitaView: ReceitaView): ResultadoOperacaoDb {
             val receita =MapReceita.receitaViewToReceita(receitaView).copy(isUserList = true)
             val resultadoOperacaoDb =criarReceita(receita)
                return  resultadoOperacaoDb
    }

    override suspend fun getListBanner(): ResultConsultasReceita {
           try {
               val receitaBanner =repository.getListBanner()
                val receitaViewBannerList =  MapReceita.toListReceitaView(receitaBanner)
               return ResultConsultasReceita(
                   true,"lista banner", receitaViewBannerList
               )

           }catch (ex:Exception){
               ex.stackTrace
               return ResultConsultasReceita(
                   false,"erro ao carregar lista", listOf()
               )
           }
    }
}