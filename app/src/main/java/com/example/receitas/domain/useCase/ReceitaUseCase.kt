package com.example.receitas.domain.useCase

import android.util.Log
import com.example.receitas.data.repository.interf.IRepository
import com.example.receitas.domain.model.Receita
import com.example.receitas.domain.interf.IReceitaUseCase
import com.example.receitas.mapReceita.MapReceita
import com.example.receitas.presentation.model.ReceitaView
import javax.inject.Inject

class ReceitaUseCase @Inject constructor(
    private val repository: IRepository
    ) : IReceitaUseCase {
    override suspend fun listarReceitar(): List<Receita>? {
         try {
             val listReceita = repository.recuperarListaReceitas()
             listReceita.forEach {
                 Log.i("Receita-", "recuperarListaReceitasUSECASE: ${it.titulo}")
             }
             return listReceita
         }catch (ex:Exception){
             Log.i("ERRO", "listarReceitar: ${ex.message}")
             throw Exception("Erro ao recuperar lista")
         }
    }

    override suspend fun recuperaReceitaPorId(id: Int): ReceitaView {
           try {
               val receita = repository.recuperaReceitaPorId(id)
               Log.i("Receita-", "recuperarReceitaid: ${receita.titulo}")
                   val receitaView = MapReceita.receitaToReceitaView(receita)
               return receitaView
           }catch(ex :Exception){
               throw Exception("Receita nao encontrada")
           }
    }

    override suspend fun criarReceita(receita: Receita): ReceitaView {
        try {
            val receitaCriada = repository.criarReceita(receita)
            Log.i("Receita-", "criarReceita: ${receitaCriada.titulo}")
                 val receitaVI=MapReceita.receitaToReceitaView(receita)
             return receitaVI
        }catch (ex:Exception){
            throw Exception("Receita nao encontrada")
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

    override suspend fun deletarReceita(id: Int): Boolean {
        try {
           val resultado = repository.deletarReceita(id)
            Log.i("Receita-", "deletarReceita: ${resultado} ")
            return true
        }catch (ex:Exception){
            throw Exception("Erro ao deletar Receita")
        }
    }

}