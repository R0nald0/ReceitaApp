package com.example.receitas.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.receitas.domain.interf.IReceitaUseCase
import com.example.receitas.domain.results.ResultadoOperacaoDb
import com.example.receitas.domain.results.VerificaCampos
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
    private val _verifiacaCampoLiveData =MutableLiveData<VerificaCampos>()
    val verifiacaCampoLiveData :LiveData<VerificaCampos>
    get() = _verifiacaCampoLiveData

    private val _resultadoOperacaoDbLiveData = MutableLiveData<ResultadoOperacaoDb>()
    val resultadoOperacaoDb :  LiveData<ResultadoOperacaoDb>
    get() = _resultadoOperacaoDbLiveData

    val isCreateReceita = MutableLiveData<Boolean>()

    fun criarReceita(receitaViewCreate : ReceitaViewCreate){

           val receita =MapReceita.receitaViewCreateToReceita(receitaViewCreate)
            isCreateReceita.value= true
        viewModelScope.launch {
            val resultado = useCaseReceita.criarReceita(receita)
            _resultadoOperacaoDbLiveData.postValue(resultado)
        }
    }

    fun verificarCampos(receitaCreat: ReceitaViewCreate){
        val result = useCaseReceita.verificarCampos(receitaCreat)
         _verifiacaCampoLiveData.value = result
    }
    fun editareceita(receitaView: ReceitaView){
        isCreateReceita.value= false
         viewModelScope.launch {
             val retorno = useCaseReceita.atualizarReceita(receitaView)
             _resultadoOperacaoDbLiveData.postValue(retorno)
             }
        }

}