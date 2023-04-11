package com.example.receitas.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.receitas.domain.interf.IReceitaUseCase
import com.example.receitas.domain.results.AppStateList

import com.example.receitas.domain.results.ResultConsultasAreas
import com.example.receitas.domain.results.ResultConsultasReceita
import com.example.receitas.mapReceita.MapReceita
import com.example.receitas.presentation.model.ReceitaView
import com.example.receitas.shared.constant.Const

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

        val listaReceitaLiveData = MutableLiveData<List<ReceitaView>>()
        val appStateList = MutableLiveData<AppStateList>()
        private var _listaAreaCarregad =   false
        private var _receitalistCarregada = false



    init {
        appStateList.value = AppStateList.loading
    }
    fun listarAreas(){
           viewModelScope.launch {
                    val resultadoConsulta = receitaUseCase.listarArea()
                   areasResultadoConsultadLiveData.postValue(resultadoConsulta)
                  _listaAreaCarregad = true
                 verifyListsChanging()
           }
       }

    fun verifyListsChanging(){
        Const.exibilog("area  ${_listaAreaCarregad} receita ${_receitalistCarregada}")
        if (_listaAreaCarregad  && _receitalistCarregada){
                appStateList.postValue( AppStateList.loaded)
        }
    }
    fun listar(){
           viewModelScope.launch(Dispatchers.IO) {
               val resultado = receitaUseCase.listarReceita()
                resultadoListConsultaLiveData.postValue(resultado)
               _receitalistCarregada = true
               verifyListsChanging()
           }

     }

    fun recuperarArea(area:String?){
        if (area !=null ){
            viewModelScope.launch {
                areaNameObserve.value = area
                val  listaArea  = receitaUseCase.recuperarReceitasPorArea("${areaNameObserve.value}")
                 val listReceitasFromAreas= MapReceita.toListReceitaView(listaArea)
                listaReceitaLiveData.postValue(listReceitasFromAreas)
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