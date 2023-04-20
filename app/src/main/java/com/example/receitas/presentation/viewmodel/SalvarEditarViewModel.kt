package com.example.receitas.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.receitas.domain.interf.IReceitaUseCase
import com.example.receitas.domain.results.AppStateRequest
import com.example.receitas.domain.results.ResultadoOperacaoDb
import com.example.receitas.domain.results.VerificaCampos
import com.example.receitas.mapReceita.MapReceita
import com.example.receitas.presentation.model.ReceitaView
import com.example.receitas.presentation.model.ReceitaViewCreate
import com.example.receitas.shared.constant.Const
import com.example.receitas.shared.permissions.Permission
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.nio.channels.MulticastChannel
import javax.inject.Inject
import kotlin.time.Duration

@HiltViewModel
class SalvarEditarViewModel @Inject constructor(
    private val useCaseReceita:IReceitaUseCase
) :ViewModel(){
    private val _verifiacaCampoLiveData =MutableLiveData<VerificaCampos>()
    val verifiacaCampoLiveData :LiveData<VerificaCampos>
    get() = _verifiacaCampoLiveData

    private val _statusApp  =MutableLiveData<AppStateRequest>()
    val statusApp :LiveData<AppStateRequest>
        get() = _statusApp


    private val _resultadoOperacaoDbLiveData = MutableLiveData<ResultadoOperacaoDb>()
    val resultadoOperacaoDb :  LiveData<ResultadoOperacaoDb>
      get() = _resultadoOperacaoDbLiveData

    fun criarReceita(receitaViewCreate : ReceitaViewCreate){
           val receita =MapReceita.receitaViewCreateToReceita(receitaViewCreate)

        viewModelScope.launch {
            val resultado = useCaseReceita.criarReceita(receita)
            _resultadoOperacaoDbLiveData.postValue(resultado)
        }
    }

    fun verificarCampos(receitaCreat: ReceitaViewCreate){

        viewModelScope.launch{
            _statusApp.value = AppStateRequest.loading
            val result = useCaseReceita.verificarCampos(receitaCreat)
            _verifiacaCampoLiveData.value = result
            _statusApp.value = AppStateRequest.loaded
        }
    }
    fun editareceita(receitaView: ReceitaView){

         viewModelScope.launch {
             val retorno = useCaseReceita.atualizarReceita(receitaView)
             _resultadoOperacaoDbLiveData.postValue(retorno)
             }
        }


}