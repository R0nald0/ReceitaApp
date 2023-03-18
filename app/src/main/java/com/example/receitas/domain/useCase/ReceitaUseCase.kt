package com.example.receitas.domain.useCase

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
import com.example.receitas.shared.constant.Const


import javax.inject.Inject

class ReceitaUseCase @Inject constructor(
    private val repository: IRepository
    ) : IReceitaUseCase {

    override suspend fun listarArea():ResultConsultasAreas {

          try {
              val areas  = repository.recuperarListaArea()
              return   ResultConsultasAreas(true,"Sucesso ao carregar lista de areas",areas)

          }catch(ex:Exception){
              ResultConsultasAreas(false,"erro ao carregar lista de areas", listOf())
               throw Exception("erro a :${ex.message}")
          }
    }
    override suspend fun listarReceita(): ResultConsultasReceita {
         try {
             val listReceita = repository.recuperarListaReceitas()

             val listaReceitaview = MapReceita.toListReceitaView(listReceita)
             return  ResultConsultasReceita(
                 true,
                 "Lista carregada com sucesso",
                 listaReceitaview
             )

         }catch (ex:Exception){
             ex.stackTrace
             return  ResultConsultasReceita(
                 true,
                 "Erro ao recuperar lista  ${ex.message}",
                 listOf()
             )
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
    override suspend fun getReceitaByName(receitaName: String): ReceitaView {
        try {
            val receitaView =  repository.recuperaReceitaPorNome(receitaName)
            if (receitaView !=null){
                return  MapReceita.receitaToReceitaView(receitaView)
            }else{
                return  ReceitaView("",false,"null","", "","", "","")
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
            return    return  ReceitaView("",false,"null","", "","", "","")

        }catch (ex:Exception){
            throw Exception("Errro ao recuperar Receita :${ex.message}")
        }
    }
    override suspend fun criarReceita(receita: Receita): ResultadoOperacaoDb {
        try {


            if ( receita.titulo.isEmpty())
                return ResultadoOperacaoDb(false,"preenchos campos o nome")

            if (receita.ingredientes.isNullOrEmpty())
                return ResultadoOperacaoDb(false,"preencha os ingredientes")
            if (receita.tempo.isEmpty())
                receita.tempo ="00"
            if (receita.instrucoes.isEmpty())
                return ResultadoOperacaoDb(false,"preenchos as instrucoes")

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
           Const.exibilog("user List  : ${receita.isUserList}")
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