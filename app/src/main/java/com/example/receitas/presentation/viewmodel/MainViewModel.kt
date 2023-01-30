package com.example.receitas.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.receitas.R
import com.example.receitas.domain.interf.IReceitaUseCase
import com.example.receitas.mapReceita.MapReceita
import com.example.receitas.presentation.model.ReceitaView

import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val  receitaUseCase: IReceitaUseCase
) : ViewModel() {

      private val listaReceitaLiveData = MutableLiveData<List<ReceitaView>>()
      private val  receitaLiveData = MutableLiveData<ReceitaView>()
      var receitasViews = mutableListOf<ReceitaView>()

      fun listar(): MutableLiveData<List<ReceitaView>>{
          viewModelScope.launch {
            receitasViews  = (receitaUseCase.listarReceitar()?.let {
                MapReceita.toListReceitaView(it) } as MutableList<ReceitaView>?)!!
                 listaReceitaLiveData.value = receitasViews
                  }
            return listaReceitaLiveData
     }

    fun criarReceita(){
        val receitaView = ReceitaView(
            4,"Cafe", R.drawable.cafe_cna,"54min", listOf("leite","acucar","po"))
          viewModelScope.launch {
              val  receita = MapReceita.receitaViewToReceita(receitaView)
               receitaLiveData.postValue(receitaUseCase.criarReceita(receita))
               receitasViews.add(receitaUseCase.criarReceita(receita))
          }
             listaReceitaLiveData.value =receitasViews
    }

}