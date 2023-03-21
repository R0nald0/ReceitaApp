package com.example.receitas.domain.results

import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.example.receitas.data.model.Dto.Area
import com.example.receitas.presentation.model.ReceitaView

data class ResultConsultasReceita (
      val sucesso:Boolean = false,
      val mensagem :String ,
      val list:List<ReceitaView>
)

data class ResultConsultasAreas (
      val sucesso:Boolean = false,
      val mensagem :String ,
      val list:List<Area>
)