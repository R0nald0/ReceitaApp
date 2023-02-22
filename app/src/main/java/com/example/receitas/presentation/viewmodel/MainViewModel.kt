package com.example.receitas.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.receitas.R
import com.example.receitas.constant.Const
import com.example.receitas.data.model.Dto.Area
import com.example.receitas.domain.interf.IReceitaUseCase
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
        private val listaAreaLieData  = MutableLiveData<List<Area>>()

        private val userListReceitaLiveData = MutableLiveData<List<ReceitaView>>()
        val userListReceita : MutableLiveData<List<ReceitaView>>
        get() = userListReceitaLiveData

        val areas : MutableLiveData<List<Area>>
        get() = listaAreaLieData

        private val areaNameObserve = MutableLiveData<String>()
        val areaName : MutableLiveData<String>
        get() = areaNameObserve

        val listaReceitaLiveData = MutableLiveData<List<ReceitaView>>()
        var receitasViews = mutableListOf<ReceitaView>()

        var listCarregada = MutableLiveData<Boolean>()
        var listVazia = MutableLiveData<Boolean>()


    init {
        listar()
        listarAreas()
        recuperarArea("Unknown")
    }
    fun listarAreas(){
           viewModelScope.launch {
                listCarregada.postValue(false)
               if (receitaUseCase.listarArea() !=null){
                   listaAreaLieData.postValue(receitaUseCase.listarArea())
                   listCarregada.postValue(true)
               }
           }
       }

    fun listar(){
           viewModelScope.launch(Dispatchers.IO) {
               listCarregada.postValue(false)
               receitasViews = receitaUseCase.listarReceitar().map {
                   MapReceita.receitaToReceitaView(it)
               } as MutableList<ReceitaView>

               if(receitasViews.isNotEmpty()){
                     userListReceitaLiveData.postValue(receitasViews)
                    listCarregada.postValue(true)
                    listVazia.postValue(false)
               }else{
                   listVazia.postValue(true)
               }
           }

     }

    fun recuperarArea(area:String?){
        if (area !=null ){
            viewModelScope.launch {
                areaName.value = area
                Const.exibilog("${areaName.value}///////\n")
                val  listaArea  = receitaUseCase.recuperarReceitasPorArea("${areaName.value}")

                 listaReceitaLiveData.postValue(MapReceita.toListReceitaView(listaArea))

                listaArea.forEach {
                    Const.exibilog("${it.titulo} ingredientes \n\t: ${it.ingredientes}" )
                }
            }
        }

    }

    fun criarReceita(){
        val receitaViewCreate = ReceitaViewCreate( "Cafe", R.drawable.cafe_cna,"54min","instucao da receita" ,listOf("leite","acucar","po de café"))

          val receita= MapReceita.receitaViewCreateToReceita(receitaViewCreate)
          viewModelScope.launch {
              Const.exibilog("ObjectId bd :: ${receita.idRealm}")
                 if (receitaUseCase.criarReceita(receita)){
                     listVazia.postValue(false)
                     listar()
                 }else{
                     Const.exibilog("Erro ao salvar")
                 }
          }
    }
    fun deletarTodos(receitaView: ReceitaView){
              val receita = MapReceita.receitaViewToReceita(receitaView)

            viewModelScope.launch {
                 if (receitaUseCase.deletarReceita(receita)){
                     Const.exibilog("${receitaView.titulo}")
                     Const.exibilog("id delte ${receitaView.titulo}")


                     receitasViews = MapReceita.toListReceitaView(receitaUseCase.listarReceitar()) as MutableList<ReceitaView>
                     listaReceitaLiveData.postValue(receitasViews)
                 }else{
                     Const.exibilog("Erro ao deletar")
                 }

            }
    }


}