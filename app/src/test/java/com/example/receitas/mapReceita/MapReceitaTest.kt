package com.example.receitas.mapReceita

import com.example.receitas.data.model.ReceitaDAO
import com.example.receitas.domain.model.Receita
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class MapReceitaTest {

    @Before
    fun setUp() {
    }

    @Test
    fun receitaDataToReceita() {
      val receitaDAO =   ReceitaDAO().apply {
          this.nome= "Lasanha"
          this.time = "34"
          this.instrucao= "Deixar no fogo por 4 minutos"
          this.image ="primeira imagem"
          this.imageLink = "link imagem"
          this.isUserList = true
      }

     val receita =   MapReceita.receitaDaoToReceita(receitaDAO)

        assertThat(receita).isInstanceOf(Receita::class.java)
    }
}