package com.example.receitas.domain.results

import com.example.receitas.presentation.model.ReceitaView
import com.example.receitas.presentation.model.ReceitaViewCreate

class VerificaCampos( receitaView : ReceitaViewCreate) {

     var sucesso : Boolean =false
     val receitaView : ReceitaViewCreate

     init {
         this.receitaView = receitaView
         this.verificarReceitaC(receitaView)
     }

    private fun  verificarReceitaC(receitaView:ReceitaViewCreate){
        if (receitaView.titulo.isNotEmpty() &&
            receitaView.ingredientes.isNotEmpty() &&
            receitaView.instrucao.isNotEmpty()) sucesso = true
        }

 }
