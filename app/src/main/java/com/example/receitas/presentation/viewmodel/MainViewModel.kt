package com.example.receitas.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.receitas.domain.interf.IReceitaUseCase
import com.example.receitas.domain.results.AppStateRequest

import com.example.receitas.domain.results.ResultConsultasAreas
import com.example.receitas.domain.results.ResultConsultasReceita
import com.example.receitas.mapReceita.MapReceita
import com.example.receitas.presentation.model.ReceitaView

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val  receitaUseCase: IReceitaUseCase
) : ViewModel() {

       val resultadoListConsultaLiveData = MutableLiveData<ResultConsultasReceita>()
       val pesquisaLiveData = MutableLiveData<ResultConsultasReceita>()
       val receitaBannerResultListLiveData = MutableLiveData<ResultConsultasReceita>()

        private val areasResultadoConsultadLiveData  = MutableLiveData<ResultConsultasAreas>()
        val areaRsultadoConsulta : LiveData<ResultConsultasAreas>
        get() = areasResultadoConsultadLiveData

        private val _areaNameObserve = MutableLiveData<String>()
        val areaNameObserve : MutableLiveData<String>
        get() = _areaNameObserve

        val listaReceitaApiLiveData = MutableLiveData<List<ReceitaView>>()
        val appStateRequest = MutableLiveData<AppStateRequest>()
        private var _listaAreaCarregad =   false
        private var _receitalistCarregada = false

    init {
        appStateRequest.value = AppStateRequest.loading
    }
    fun listarAreas(){
           viewModelScope.launch(Dispatchers.IO) {
                    val resultadoConsulta = receitaUseCase.listarArea()
                   areasResultadoConsultadLiveData.postValue(resultadoConsulta)
               _listaAreaCarregad = true
                 verifyListsChanging()
           }
       }

    private fun verifyListsChanging(){
        if (_listaAreaCarregad  && _receitalistCarregada){
                appStateRequest.postValue( AppStateRequest.loaded)
        }
    }
    fun getListUserReceitas(){
           viewModelScope.launch(Dispatchers.IO) {
               val resultado = receitaUseCase.listarReceita()
                resultadoListConsultaLiveData.postValue(resultado)
               _receitalistCarregada = true
               verifyListsChanging()
           }

     }

    fun recuperarArea(area:String?){
        if (area != null) {
            if (area.isNotEmpty() ){
                viewModelScope.launch {
                    appStateRequest.value = AppStateRequest.loading
                    areaNameObserve.value = area
                     val  listaArea  = receitaUseCase.getReceitaByAreaName("${areaNameObserve.value}")
                    listaReceitaApiLiveData.postValue(listaArea)
                    appStateRequest.value=AppStateRequest.loaded
                }
            }else{
                listaReceitaApiLiveData.postValue(listOf())
            }
        }
    }

    fun pesquisarReceita(nomePesquisa:String){
          viewModelScope.launch {
             val resultadoPesquisa = receitaUseCase.pesquisarReceitaPorTitulo(nomePesquisa)
              pesquisaLiveData.postValue(resultadoPesquisa)
            }
    }

    fun getListReceitaBanner(){
           viewModelScope.launch {
             val result  = receitaUseCase.getListBanner()
               receitaBannerResultListLiveData.value =result
           }
    }
}