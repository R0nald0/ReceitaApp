package com.example.receitas.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.receitas.domain.interf.IReceitaUseCase
import com.example.receitas.domain.results.ResultadoOperacaoDb
import com.example.receitas.mapReceita.MapReceita
import com.example.receitas.presentation.model.ReceitaView
import com.example.receitas.presentation.model.ReceitaViewCreate
import com.example.receitas.shared.constant.Const
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SalvarEditarViewModel @Inject constructor(
    private val useCaseReceita:IReceitaUseCase
) :ViewModel(){

    private val _resultadoOperacaoDbLiveData = MutableLiveData<ResultadoOperacaoDb>()
    val resultadoOperacaoDb :  LiveData<ResultadoOperacaoDb>
    get() = _resultadoOperacaoDbLiveData

    fun criarReceita(receitaViewCreate : ReceitaViewCreate){

        Const.exibilog("receita name ${receitaViewCreate.titulo}")
        viewModelScope.launch {
            val resultado = useCaseReceita.criarReceita(receitaViewCreate)
            _resultadoOperacaoDbLiveData.postValue(resultado)
        }

    }

    fun editareceita(receitaView: ReceitaView){
         viewModelScope.launch {
             Const.exibilog("imagem ${receitaView.Imagem}")
             Const.exibilog("id ${receitaView.idRealm}")

             val retorno = useCaseReceita.atualizarReceita(receitaView)
             _resultadoOperacaoDbLiveData.postValue(retorno)
             }
         }

}