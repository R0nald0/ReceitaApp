package com.example.receitas.domain.results

import com.example.receitas.data.remote.model.Dto.AreaDTO
import com.example.receitas.domain.model.Area
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