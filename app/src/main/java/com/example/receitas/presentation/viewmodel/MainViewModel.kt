package com.example.receitas.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.receitas.R
import com.example.receitas.shared.constant.Const
import com.example.receitas.data.model.Dto.Area
import com.example.receitas.domain.interf.IReceitaUseCase
import com.example.receitas.domain.results.ResultConsultas
import com.example.receitas.domain.results.ResultadoOperacaoDb
import com.example.receitas.mapReceita.MapReceita
import com.example.receitas.presentation.model.ReceitaView
import com.example.receitas.presentation.model.ReceitaViewCreate

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val  receitaUseCase: IReceitaUseCase
) : ViewModel() {
        private val areasResultadoConsultadLiveData  = MutableLiveData<ResultConsultas>()
        val areaRsultadoConsulta : MutableLiveData<ResultConsultas>
        get() = areasResultadoConsultadLiveData

         val resultadoListConsultaLiveData = MutableLiveData<ResultConsultas>()


        private val areaNameObserve = MutableLiveData<String>()
        val areaName : MutableLiveData<String>
        get() = areaNameObserve

        val listaReceitaLiveData = MutableLiveData<List<ReceitaView>>()
        var receitasViews = mutableListOf<ReceitaView>()

        private val resultadoOperacaoDbLiveData = MutableLiveData<ResultadoOperacaoDb>()
        val resultadoOperacaoDb :MutableLiveData<ResultadoOperacaoDb>
        get() = resultadoOperacaoDbLiveData


    fun listarAreas(){
           viewModelScope.launch {
                    val resultadoConsulta = receitaUseCase.listarArea()
                   areasResultadoConsultadLiveData.postValue(resultadoConsulta)
           }
       }

    fun listar(){
           viewModelScope.launch(Dispatchers.IO) {
               val resultado = receitaUseCase.listarReceitar()
                   resultadoListConsultaLiveData.postValue(resultado)
           }

     }

    fun recuperarArea(area:String?){
        if (area !=null ){
            viewModelScope.launch {
                areaName.value = area
                val  listaArea  = receitaUseCase.recuperarReceitasPorArea("${areaName.value}")
                 listaReceitaLiveData.postValue(MapReceita.toListReceitaView(listaArea))
            }
        }

    }

    fun criarReceita(receitaViewCreate : ReceitaViewCreate){

           Const.exibilog("receita name ${receitaViewCreate.titulo}")
          viewModelScope.launch {
             val resultado = receitaUseCase.criarReceita(receitaViewCreate)
              resultadoOperacaoDbLiveData.postValue(resultado)
          }

    }
    fun deletarTodos(receitaView: ReceitaView){
              val receita = MapReceita.receitaViewToReceita(receitaView)

            viewModelScope.launch {
                 if (receitaUseCase.deletarReceita(receita)){
                     Const.exibilog("${receitaView.titulo}")
                     Const.exibilog("id delte ${receitaView.titulo}")


                 }else{
                     Const.exibilog("Erro ao deletar")
                 }

            }
    }

    fun pesquisarReceita(nomePesquisa:String){

    }
}